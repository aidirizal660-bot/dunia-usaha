package com.example.audio

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.MediaPlayer
import android.net.Uri
import android.provider.OpenableColumns
import com.example.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.min
import kotlin.math.sin

class SoundManager {
    private val sampleRate = 22050
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var bgmTrack: AudioTrack? = null
    private var customMediaPlayer: MediaPlayer? = null
    private var clickSamples: ShortArray? = null
    private var successSamples: ShortArray? = null
    private var errorSamples: ShortArray? = null
    private var fanfareSamples: ShortArray? = null

    @Volatile
    private var musicEnabled = true

    @Volatile
    private var isPausedByLifecycle = false

    @Volatile
    private var bgmSuppressed = false

    @Volatile
    private var initialized = false

    fun initializeAndStart(context: Context? = null) {
        if (initialized) return
        initialized = true
        scope.launch {
            runCatching {
                clickSamples = synthesizeClickSfx()
                successSamples = synthesizeSuccessSfx()
                errorSamples = synthesizeErrorSfx()
                fanfareSamples = synthesizeFanfareSfx()

                var mediaStarted = false
                if (context != null) {
                    val savedUriStr = getPrefs(context).getString(KEY_AUDIO_URI, null)
                    if (!savedUriStr.isNullOrBlank()) {
                        mediaStarted = startMediaPlayerFromUri(context, Uri.parse(savedUriStr))
                        if (!mediaStarted) {
                            clearSavedPrefs(context)
                        }
                    }
                    if (!mediaStarted) {
                        mediaStarted = startBundledRawBacksound(context)
                    }
                }

                // Fallback synthesized track only if bundled MP3 cannot be opened
                if (!mediaStarted) {
                    val bgm = synthesizeCheerfulKidsInstrumentalLoop()
                    val track = buildStaticTrack(bgm.size)
                    if (track != null) {
                        track.write(bgm, 0, bgm.size)
                        track.setLoopPoints(0, bgm.size, -1)
                        bgmTrack = track
                        if (musicEnabled && !isPausedByLifecycle && !bgmSuppressed) {
                            track.play()
                        }
                    }
                }
            }
        }
    }

    fun setBgmSuppressed(suppressed: Boolean) {
        bgmSuppressed = suppressed
        syncActiveBgmPlayback()
    }

    fun getSavedCustomAudioName(context: Context): String? {
        return runCatching {
            val savedName = getPrefs(context).getString(KEY_AUDIO_NAME, null)
            if (!savedName.isNullOrBlank()) {
                savedName
            } else {
                findBundledAssetFileName(context)
            }
        }.getOrNull()
    }

    fun loadCustomAudio(
        context: Context,
        uri: Uri,
        onComplete: (String?) -> Unit = {}
    ) {
        scope.launch(Dispatchers.IO) {
            runCatching {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            val displayName = resolveDisplayName(context, uri)
            bgmSuppressed = false
            val loaded = startMediaPlayerFromUri(context, uri)
            if (loaded) {
                getPrefs(context).edit()
                    .putString(KEY_AUDIO_URI, uri.toString())
                    .putString(KEY_AUDIO_NAME, displayName)
                    .apply()
                pauseSynthesizedTrack()
            }
            withContext(Dispatchers.Main) {
                onComplete(if (loaded) displayName else null)
            }
        }
    }

    fun clearCustomAudio(context: Context) {
        scope.launch(Dispatchers.IO) {
            clearSavedPrefs(context)
            releaseCustomPlayer()
            if (!startBundledRawBacksound(context)) {
                syncActiveBgmPlayback()
            }
        }
    }

    fun setMusicEnabled(enabled: Boolean) {
        musicEnabled = enabled
        syncActiveBgmPlayback()
    }

    fun onLifecyclePause() {
        isPausedByLifecycle = true
        runCatching {
            customMediaPlayer?.let {
                if (it.isPlaying) it.pause()
            }
            bgmTrack?.let {
                if (it.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    it.pause()
                }
            }
        }
    }

    fun onLifecycleResume() {
        isPausedByLifecycle = false
        syncActiveBgmPlayback()
    }

    private fun syncActiveBgmPlayback() {
        runCatching {
            val custom = customMediaPlayer
            if (custom != null) {
                val shouldPlayCustom = musicEnabled && !isPausedByLifecycle && !bgmSuppressed
                if (shouldPlayCustom) {
                    if (!custom.isPlaying) custom.start()
                } else {
                    if (custom.isPlaying) custom.pause()
                }
                return
            }
            val track = bgmTrack ?: return
            val shouldPlayFallback = musicEnabled && !isPausedByLifecycle && !bgmSuppressed
            if (shouldPlayFallback) {
                if (track.playState != AudioTrack.PLAYSTATE_PLAYING) {
                    track.play()
                }
            } else {
                if (track.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    track.pause()
                }
            }
        }
    }

    fun playClick() {
        if (!musicEnabled) return
        playShortSfx(clickSamples)
    }

    fun playSuccess() {
        if (!musicEnabled) return
        playShortSfx(successSamples)
    }

    fun playError() {
        if (!musicEnabled) return
        playShortSfx(errorSamples)
    }

    fun playFanfare() {
        if (!musicEnabled) return
        playShortSfx(fanfareSamples)
    }

    fun release() {
        releaseCustomPlayer()
        runCatching {
            bgmTrack?.stop()
            bgmTrack?.release()
            bgmTrack = null
        }
        scope.cancel()
    }

    private fun startMediaPlayerFromUri(context: Context, uri: Uri): Boolean {
        return runCatching {
            val player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                setDataSource(context, uri)
                isLooping = true
                setVolume(0.62f, 0.62f)
                prepare()
            }
            releaseCustomPlayer()
            customMediaPlayer = player
            if (musicEnabled && !isPausedByLifecycle && !bgmSuppressed) {
                player.start()
            }
            true
        }.getOrDefault(false)
    }

    private fun startBundledRawBacksound(context: Context): Boolean {
        return runCatching {
            val afd = context.resources.openRawResourceFd(R.raw.backsound_ceria) ?: return false
            afd.use {
                val player = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    setDataSource(it.fileDescriptor, it.startOffset, it.length)
                    isLooping = true
                    setVolume(0.65f, 0.65f)
                    prepare()
                }
                releaseCustomPlayer()
                customMediaPlayer = player
                if (musicEnabled && !isPausedByLifecycle && !bgmSuppressed) {
                    player.start()
                }
            }
            true
        }.getOrDefault(false)
    }

    private fun findBundledAssetFileName(context: Context): String? {
        return null
    }

    private fun pauseSynthesizedTrack() {
        runCatching {
            bgmTrack?.let {
                if (it.playState == AudioTrack.PLAYSTATE_PLAYING) {
                    it.pause()
                }
            }
        }
    }

    private fun releaseCustomPlayer() {
        runCatching {
            customMediaPlayer?.let {
                if (it.isPlaying) it.stop()
                it.release()
            }
            customMediaPlayer = null
        }
    }

    private fun resolveDisplayName(context: Context, uri: Uri): String {
        val queried = runCatching {
            context.contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) cursor.getString(0) else null
            }
        }.getOrNull()
        return queried?.takeIf { it.isNotBlank() } ?: uri.lastPathSegment ?: "Audio Kustom"
    }

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun clearSavedPrefs(context: Context) {
        getPrefs(context).edit().remove(KEY_AUDIO_URI).remove(KEY_AUDIO_NAME).apply()
    }

    private fun playShortSfx(samples: ShortArray?) {
        val data = samples ?: return
        scope.launch {
            runCatching {
                val track = buildStaticTrack(data.size) ?: return@runCatching
                track.write(data, 0, data.size)
                track.setNotificationMarkerPosition(data.size)
                track.setPlaybackPositionUpdateListener(object : AudioTrack.OnPlaybackPositionUpdateListener {
                    override fun onMarkerReached(t: AudioTrack?) {
                        runCatching {
                            t?.stop()
                            t?.release()
                        }
                    }

                    override fun onPeriodicNotification(t: AudioTrack?) = Unit
                })
                track.play()
            }
        }
    }

    private fun buildStaticTrack(sampleCount: Int): AudioTrack? {
        return runCatching {
            val bufferBytes = sampleCount * 2
            val minBytes = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(bufferBytes)
            AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setTransferMode(AudioTrack.MODE_STATIC)
                .setBufferSizeInBytes(maxOf(bufferBytes, minBytes))
                .build()
        }.getOrNull()
    }

    private fun synthesizeCheerfulKidsInstrumentalLoop(): ShortArray {
        val loopSeconds = 16.0
        val totalSamples = (sampleRate * loopSeconds).toInt()
        val mix = DoubleArray(totalSamples)

        // Cheerful C-major kids instrumental pitches (Hz)
        val c3 = 130.81; val f3 = 174.61; val g3 = 196.00; val a3 = 220.00
        val c4 = 261.63; val d4 = 293.66; val e4 = 329.63; val f4 = 349.23
        val g4 = 392.00; val a4 = 440.00; val b4 = 493.88
        val c5 = 523.25; val d5 = 587.33; val e5 = 659.25; val f5 = 698.46
        val g5 = 783.99; val a5 = 880.00; val c6 = 1046.50

        // Playful 8-bar xylophone/glockenspiel lead melody (120 BPM, 0.25s per eighth note)
        val melody = listOf(
            // Bar 1 (C)
            0.00 to e5, 0.25 to g5, 0.50 to c6, 0.75 to g5,
            1.00 to e5, 1.25 to g5, 1.50 to a5, 1.75 to g5,
            // Bar 2 (F -> G)
            2.00 to f5, 2.25 to a5, 2.50 to g5, 2.75 to f5,
            3.00 to e5, 3.25 to d5, 3.50 to c5, 3.75 to d5,
            // Bar 3 (C)
            4.00 to e5, 4.25 to g5, 4.50 to e5, 4.75 to c5,
            5.00 to d5, 5.25 to e5, 5.50 to g5, 5.75 to c6,
            // Bar 4 (G -> C)
            6.00 to a5,
            6.25 to g5, 6.50 to e5, 6.75 to d5,
            7.00 to c5, 7.50 to g5,
            // Bar 5 (Am -> F)
            8.00 to a5, 8.25 to g5, 8.50 to e5, 8.75 to a5,
            9.00 to f5, 9.25 to a5, 9.50 to c6, 9.75 to a5,
            // Bar 6 (C -> G)
            10.00 to g5, 10.25 to e5, 10.50 to c5, 10.75 to e5,
            11.00 to d5, 11.25 to e5, 11.50 to g5, 11.75 to e5,
            // Bar 7 (F -> G)
            12.00 to f5, 12.25 to g5, 12.50 to a5, 12.75 to f5,
            13.00 to e5, 13.25 to g5, 13.50 to c6, 13.75 to a5,
            // Bar 8 (G7 -> C turnaround)
            14.00 to g5, 14.25 to f5, 14.50 to e5, 14.75 to d5,
            15.00 to c5, 15.50 to g5
        )

        // Warm ukulele-style off-beat chord accompaniment across 8 bars
        val chords = listOf(
            // Bar 1-2: C, F, G
            0.5 to listOf(c4, e4, g4), 1.5 to listOf(c4, e4, g4),
            2.5 to listOf(f4, a4, c5), 3.5 to listOf(g4, b4, d5),
            // Bar 3-4: C, G, C
            4.5 to listOf(c4, e4, g4), 5.5 to listOf(c4, e4, g4),
            6.5 to listOf(g4, b4, d5), 7.5 to listOf(c4, e4, g4),
            // Bar 5-6: Am, F, C, G
            8.5 to listOf(a4, c5, e5), 9.5 to listOf(f4, a4, c5),
            10.5 to listOf(c4, e4, g4), 11.5 to listOf(g4, b4, d5),
            // Bar 7-8: F, G, C
            12.5 to listOf(f4, a4, c5), 13.5 to listOf(c4, e4, g4),
            14.5 to listOf(g4, b4, d5), 15.5 to listOf(c4, e4, g4)
        )

        // Bouncy bass root notes on beats 1 & 3
        val bass = listOf(
            0.0 to c3, 1.0 to g3, 2.0 to f3, 3.0 to g3,
            4.0 to c3, 5.0 to g3, 6.0 to g3, 7.0 to c3,
            8.0 to a3, 9.0 to f3, 10.0 to c3, 11.0 to g3,
            12.0 to f3, 13.0 to c3, 14.0 to g3, 15.0 to c3
        )

        melody.forEach { (startSec, freq) ->
            addBrightBellNote(mix, startSec, 0.34, freq, amplitude = 0.16, decay = 7.2)
        }
        chords.forEach { (startSec, triad) ->
            triad.forEachIndexed { idx, freq ->
                addMarimbaNote(mix, startSec + idx * 0.015, 0.28, freq, amplitude = 0.055, decay = 9.0)
            }
        }
        bass.forEach { (startSec, freq) ->
            addMarimbaNote(mix, startSec, 0.48, freq, amplitude = 0.12, decay = 4.5)
        }

        // Gentle soft rhythmic pulse (light woodblock on beats 2 & 4, soft kick on 1 & 3)
        for (beat in 0 until 32) {
            val sec = beat * 0.5
            if (beat % 2 == 0) {
                addSoftKick(mix, sec, amplitude = 0.07)
            } else {
                addSoftWoodTick(mix, sec, amplitude = 0.045)
            }
        }

        return ShortArray(totalSamples) { i ->
            (mix[i].coerceIn(-0.92, 0.92) * Short.MAX_VALUE).toInt().toShort()
        }
    }

    private fun addBrightBellNote(
        buffer: DoubleArray,
        startSec: Double,
        durationSec: Double,
        freq: Double,
        amplitude: Double,
        decay: Double
    ) {
        val startIdx = (startSec * sampleRate).toInt()
        val noteLen = (durationSec * sampleRate).toInt()
        val attackSamples = (0.008 * sampleRate).toInt().coerceAtLeast(1)

        for (i in 0 until noteLen) {
            val idx = startIdx + i
            if (idx >= buffer.size) break
            val t = i.toDouble() / sampleRate
            val attack = min(1.0, i.toDouble() / attackSamples)
            val tailFade = if (i > noteLen - 200) (noteLen - i) / 200.0 else 1.0
            val env = attack * exp(-t * decay) * tailFade
            val wave = sin(2.0 * PI * freq * t) +
                0.28 * sin(2.0 * PI * freq * 2.0 * t) +
                0.08 * sin(2.0 * PI * freq * 3.0 * t)
            buffer[idx] += wave * amplitude * env
        }
    }

    private fun addMarimbaNote(
        buffer: DoubleArray,
        startSec: Double,
        durationSec: Double,
        freq: Double,
        amplitude: Double,
        decay: Double
    ) {
        val startIdx = (startSec * sampleRate).toInt()
        val noteLen = (durationSec * sampleRate).toInt()
        val attackSamples = (0.010 * sampleRate).toInt().coerceAtLeast(1)

        for (i in 0 until noteLen) {
            val idx = startIdx + i
            if (idx >= buffer.size) break
            val t = i.toDouble() / sampleRate
            val attack = min(1.0, i.toDouble() / attackSamples)
            val tailFade = if (i > noteLen - 220) (noteLen - i) / 220.0 else 1.0
            val env = attack * exp(-t * decay) * tailFade
            val wave = sin(2.0 * PI * freq * t) +
                0.22 * sin(2.0 * PI * freq * 2.0 * t) +
                0.05 * sin(2.0 * PI * freq * 3.0 * t)
            buffer[idx] += wave * amplitude * env
        }
    }

    private fun addSoftKick(buffer: DoubleArray, startSec: Double, amplitude: Double) {
        val startIdx = (startSec * sampleRate).toInt()
        val noteLen = (0.11 * sampleRate).toInt()
        for (i in 0 until noteLen) {
            val idx = startIdx + i
            if (idx >= buffer.size) break
            val t = i.toDouble() / sampleRate
            val freq = 115.0 * exp(-t * 18.0) + 48.0
            val env = exp(-t * 24.0)
            buffer[idx] += sin(2.0 * PI * freq * t) * amplitude * env
        }
    }

    private fun addSoftWoodTick(buffer: DoubleArray, startSec: Double, amplitude: Double) {
        val startIdx = (startSec * sampleRate).toInt()
        val noteLen = (0.045 * sampleRate).toInt()
        for (i in 0 until noteLen) {
            val idx = startIdx + i
            if (idx >= buffer.size) break
            val t = i.toDouble() / sampleRate
            val env = exp(-t * 65.0)
            val wave = sin(2.0 * PI * 980.0 * t) + 0.4 * sin(2.0 * PI * 1470.0 * t)
            buffer[idx] += wave * amplitude * env
        }
    }

    private fun synthesizeClickSfx(): ShortArray {
        val len = (sampleRate * 0.08).toInt()
        val buf = DoubleArray(len)
        addMarimbaNote(buf, 0.0, 0.075, 880.0, 0.28, 22.0)
        return ShortArray(len) { (buf[it].coerceIn(-0.95, 0.95) * Short.MAX_VALUE).toInt().toShort() }
    }

    private fun synthesizeSuccessSfx(): ShortArray {
        val len = (sampleRate * 0.36).toInt()
        val buf = DoubleArray(len)
        addMarimbaNote(buf, 0.0, 0.18, 659.25, 0.28, 11.0)
        addMarimbaNote(buf, 0.11, 0.24, 1046.50, 0.32, 9.0)
        return ShortArray(len) { (buf[it].coerceIn(-0.95, 0.95) * Short.MAX_VALUE).toInt().toShort() }
    }

    private fun synthesizeErrorSfx(): ShortArray {
        val len = (sampleRate * 0.32).toInt()
        val buf = DoubleArray(len)
        addMarimbaNote(buf, 0.0, 0.16, 329.63, 0.26, 12.0)
        addMarimbaNote(buf, 0.11, 0.20, 261.63, 0.26, 11.0)
        return ShortArray(len) { (buf[it].coerceIn(-0.95, 0.95) * Short.MAX_VALUE).toInt().toShort() }
    }

    private fun synthesizeFanfareSfx(): ShortArray {
        val len = (sampleRate * 0.72).toInt()
        val buf = DoubleArray(len)
        addMarimbaNote(buf, 0.00, 0.20, 523.25, 0.26, 9.0)
        addMarimbaNote(buf, 0.11, 0.20, 659.25, 0.26, 9.0)
        addMarimbaNote(buf, 0.22, 0.20, 783.99, 0.28, 9.0)
        addMarimbaNote(buf, 0.33, 0.38, 1046.50, 0.34, 6.0)
        return ShortArray(len) { (buf[it].coerceIn(-0.95, 0.95) * Short.MAX_VALUE).toInt().toShort() }
    }

    companion object {
        private const val PREFS_NAME = "dunia_usaha_audio_prefs"
        private const val KEY_AUDIO_URI = "custom_audio_uri"
        private const val KEY_AUDIO_NAME = "custom_audio_name"
        const val REFERENCE_YOUTUBE_URL = "https://youtu.be/T00HjXM2fnw?si=O8CrT1y71-Dg6s0Q"
    }
}

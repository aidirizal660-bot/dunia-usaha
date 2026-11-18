package com.example.ui.components

import android.content.Context
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.R
import com.example.model.AppRating
import com.example.model.DuniaUsahaContent
import com.example.model.SlideDestination
import com.example.ui.theme.CoralPinkDeep
import com.example.ui.theme.CreamCard
import com.example.ui.theme.InkNavy
import com.example.ui.theme.MeadowGreen
import com.example.ui.theme.MeadowMint
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import com.example.ui.theme.TealButton
import com.example.viewmodel.PresentationUiState
import java.util.Locale

private object SlideBitmapCache {
    private val cache = LruCache<Int, ImageBitmap>(12)

    fun load(context: Context, pageNumber: Int): ImageBitmap? {
        cache.get(pageNumber)?.let { return it }
        val fileName = String.format(Locale.US, "slides/slide_%02d.webp", pageNumber)
        return runCatching {
            context.assets.open(fileName).use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        }.getOrNull()?.also { bmp ->
            cache.put(pageNumber, bmp)
        }
    }
}

@Composable
fun ExactSlideStage(
    slide: SlideDestination,
    uiState: PresentationUiState,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onNavigate: (SlideDestination) -> Unit,
    onAnswerQuiz: (Int, String) -> Unit,
    onResetQuiz: () -> Unit,
    onSelectRating: (AppRating) -> Unit,
    onToggleQrDialog: (Boolean) -> Unit,
    onPlayClick: () -> Unit = {},
    onPlaySuccess: () -> Unit = {},
    onPlayError: () -> Unit = {},
    onPlayFanfare: () -> Unit = {}
) {
    val context = LocalContext.current
    val bitmap = remember(slide.pageNumber) {
        SlideBitmapCache.load(context, slide.pageNumber)
    }

    // Gentle entrance micro-scale settle on each slide
    var settled by remember(slide) { mutableStateOf(false) }
    LaunchedEffect(slide) {
        settled = true
        if (slide == SlideDestination.EVALUASI_HASIL) {
            onPlayFanfare()
        }
    }
    val entranceScale by animateFloatAsState(
        targetValue = if (settled) 1f else 0.985f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "slideSettleScale"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = entranceScale
                scaleY = entranceScale
            }
    ) {
        val stageW = maxWidth
        val stageH = maxHeight

        if (bitmap != null) {
            val motionSpecs = remember(slide) { slideMotionSpecsFor(slide) }
            when {
                slide == SlideDestination.PROFIL_TIM || slide == SlideDestination.PROFIL_DOSEN -> {
                    ProfileAnimatedSlideBackdrop(
                        slide = slide,
                        bitmap = bitmap,
                        stageW = stageW,
                        stageH = stageH
                    )
                }
                motionSpecs.isNotEmpty() -> {
                    GeneralAnimatedSlideBackdrop(
                        slide = slide,
                        bitmap = bitmap,
                        stageW = stageW,
                        stageH = stageH,
                        slices = motionSpecs
                    )
                }
                else -> {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Halaman ${slide.pageNumber}: ${slide.title}",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        if (slide in listOf(
                SlideDestination.COVER,
                SlideDestination.KENALAN,
                SlideDestination.TAHUKAH_KAMU,
                SlideDestination.EVALUASI_HASIL,
                SlideDestination.REFLEKSI,
                SlideDestination.DAFTAR_PUSTAKA
            )
        ) {
            TwinklingSparklesOverlay()
        }

        // Exact Interactive Hotspots & Visual Polish Overlays per Slide
        when (slide) {
            SlideDestination.COVER -> {
                PlayButtonAnimatedOverlay(
                    xFrac = 0.258f, yFrac = 0.46f, wFrac = 0.168f, hFrac = 0.34f,
                    stageW = stageW, stageH = stageH,
                    onStart = {
                        onPlayClick()
                        onNext()
                    }
                )
            }

            SlideDestination.HALLO -> {
                TopCornersBackNext(stageW, stageH, onBack, onNext)
            }

            SlideDestination.KENALAN -> {
                TopCornersBackNext(stageW, stageH, onBack, onNext)
            }

            SlideDestination.MENU -> {
                TopCornersBackNext(stageW, stageH, onBack, onNext)
                // Bottom-right Home house icon
                HotspotOverlay(
                    xFrac = 0.895f, yFrac = 0.81f, wFrac = 0.085f, hFrac = 0.16f,
                    stageW = stageW, stageH = stageH,
                    shape = RoundedCornerShape(16.dp),
                    description = "Kembali ke Sampul",
                    testTag = "menu_home_button",
                    onClick = { onNavigate(SlideDestination.COVER) }
                )
                // Left column menu pills (staggered entrance animation + turn white on hover/press/select)
                MenuPillWhiteHotspot(
                    xFrac = 0.098f, yFrac = 0.44f, wFrac = 0.352f, hFrac = 0.138f,
                    stageW = stageW, stageH = stageH,
                    index = 0,
                    label = "petunjuk",
                    description = "Buka Petunjuk Penggunaan",
                    testTag = "menu_petunjuk_button",
                    onSelect = { onNavigate(SlideDestination.PETUNJUK) }
                )
                MenuPillWhiteHotspot(
                    xFrac = 0.098f, yFrac = 0.618f, wFrac = 0.352f, hFrac = 0.138f,
                    stageW = stageW, stageH = stageH,
                    index = 1,
                    label = "profil",
                    description = "Buka Profil Pengembang",
                    testTag = "menu_profil_button",
                    onSelect = { onNavigate(SlideDestination.PROFIL_TIM) }
                )
                MenuPillWhiteHotspot(
                    xFrac = 0.098f, yFrac = 0.79f, wFrac = 0.352f, hFrac = 0.138f,
                    stageW = stageW, stageH = stageH,
                    index = 2,
                    label = "tujuan",
                    description = "Buka Tujuan Pembelajaran",
                    testTag = "menu_tujuan_button",
                    onSelect = { onNavigate(SlideDestination.CAPAIAN) }
                )
                // Right column menu pills (staggered entrance animation + turn white on hover/press/select)
                MenuPillWhiteHotspot(
                    xFrac = 0.54f, yFrac = 0.44f, wFrac = 0.352f, hFrac = 0.138f,
                    stageW = stageW, stageH = stageH,
                    index = 3,
                    label = "materi",
                    description = "Buka Materi Pembelajaran",
                    testTag = "menu_materi_button",
                    onSelect = { onNavigate(SlideDestination.MATERI_MENU) }
                )
                MenuPillWhiteHotspot(
                    xFrac = 0.54f, yFrac = 0.618f, wFrac = 0.352f, hFrac = 0.138f,
                    stageW = stageW, stageH = stageH,
                    index = 4,
                    label = "games",
                    description = "Buka Games Pengusaha Cilik",
                    testTag = "menu_games_button",
                    onSelect = { onNavigate(SlideDestination.GAME_ARENA) }
                )
                MenuPillWhiteHotspot(
                    xFrac = 0.54f, yFrac = 0.79f, wFrac = 0.352f, hFrac = 0.138f,
                    stageW = stageW, stageH = stageH,
                    index = 5,
                    label = "evaluasi",
                    description = "Buka Evaluasi Pembelajaran",
                    testTag = "menu_evaluasi_button",
                    onSelect = { onNavigate(SlideDestination.EVALUASI_START) }
                )
            }

            SlideDestination.PETUNJUK -> {
                // Top Menu, Back, Next with gentle pulse halos
                PulseHalo(0.022f, 0.028f, 0.072f, 0.13f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.11f, 0.028f, 0.072f, 0.13f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.905f, 0.028f, 0.072f, 0.13f, stageW, stageH, CircleShape, SunGold)
                HotspotOverlay(
                    xFrac = 0.022f, yFrac = 0.028f, wFrac = 0.072f, hFrac = 0.13f,
                    stageW = stageW, stageH = stageH, shape = CircleShape,
                    description = "Menu Utama", testTag = "slide_menu_button",
                    onClick = { onNavigate(SlideDestination.MENU) }
                )
                HotspotOverlay(
                    xFrac = 0.11f, yFrac = 0.028f, wFrac = 0.072f, hFrac = 0.13f,
                    stageW = stageW, stageH = stageH, shape = CircleShape,
                    description = "Kembali", testTag = "slide_back_button",
                    onClick = onBack
                )
                HotspotOverlay(
                    xFrac = 0.905f, yFrac = 0.028f, wFrac = 0.072f, hFrac = 0.13f,
                    stageW = stageW, stageH = stageH, shape = CircleShape,
                    description = "Lanjut", testTag = "slide_next_button",
                    onClick = onNext
                )
                // Animated 6 Petunjuk pills (staggered shimmer + white highlight on hover/press/select)
                MenuPillWhiteHotspot(0.08f, 0.385f, 0.37f, 0.18f, stageW, stageH, 0, "Mulai", "Mulai", "petunjuk_mulai", onNext)
                MenuPillWhiteHotspot(0.08f, 0.575f, 0.37f, 0.18f, stageW, stageH, 1, "Kembali", "Kembali", "petunjuk_kembali", onBack)
                MenuPillWhiteHotspot(0.08f, 0.765f, 0.37f, 0.18f, stageW, stageH, 2, "Lanjut", "Lanjut", "petunjuk_lanjut", onNext)
                MenuPillWhiteHotspot(0.545f, 0.385f, 0.37f, 0.18f, stageW, stageH, 3, "Sampul", "Sampul", "petunjuk_sampul", {
                    onNavigate(SlideDestination.COVER)
                })
                MenuPillWhiteHotspot(0.545f, 0.575f, 0.37f, 0.18f, stageW, stageH, 4, "Info", "Info Profil", "petunjuk_info", {
                    onNavigate(SlideDestination.PROFIL_TIM)
                })
                MenuPillWhiteHotspot(0.545f, 0.765f, 0.37f, 0.18f, stageW, stageH, 5, "Menu", "Menu Utama", "petunjuk_menu", {
                    onNavigate(SlideDestination.MENU)
                })
            }

            SlideDestination.PROFIL_TIM -> {
                PulseHalo(0.015f, 0.03f, 0.062f, 0.115f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.086f, 0.03f, 0.062f, 0.115f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.922f, 0.03f, 0.062f, 0.115f, stageW, stageH, CircleShape, SunGold)
                HotspotOverlay(0.015f, 0.03f, 0.062f, 0.115f, stageW, stageH, CircleShape, "Menu", "slide_menu_button", {
                    onNavigate(SlideDestination.MENU)
                })
                HotspotOverlay(0.086f, 0.03f, 0.062f, 0.115f, stageW, stageH, CircleShape, "Kembali", "slide_back_button", onBack)
                HotspotOverlay(0.922f, 0.03f, 0.062f, 0.115f, stageW, stageH, CircleShape, "Lanjut", "slide_next_button", onNext)
                ProfileCardAccentHotspot(
                    xFrac = 0.020f, yFrac = 0.330f, wFrac = 0.358f, hFrac = 0.380f,
                    stageW = stageW, stageH = stageH,
                    description = "Profil Arina Aulia Ramadhani, Kelas BC24.1, NIM 240407512004",
                    testTag = "profil_card_arina"
                )
                ProfileCardAccentHotspot(
                    xFrac = 0.620f, yFrac = 0.335f, wFrac = 0.342f, hFrac = 0.377f,
                    stageW = stageW, stageH = stageH,
                    description = "Profil Hilda Cahyani, Kelas BC24.1, NIM 240407512007",
                    testTag = "profil_card_hilda"
                )
                ProfileCardAccentHotspot(
                    xFrac = 0.310f, yFrac = 0.610f, wFrac = 0.378f, hFrac = 0.362f,
                    stageW = stageW, stageH = stageH,
                    description = "Profil Andi Aisya Putri Andini, Kelas BC24.1, NIM 240407511015",
                    testTag = "profil_card_andi_aisya"
                )
            }

            SlideDestination.PROFIL_DOSEN -> {
                PulseHalo(0.015f, 0.03f, 0.115f, 0.17f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.87f, 0.03f, 0.115f, 0.17f, stageW, stageH, CircleShape, SunGold)
                TopMenuAndNext(stageW, stageH, { onNavigate(SlideDestination.MENU) }, onNext)
                ProfileCardAccentHotspot(
                    xFrac = 0.184f, yFrac = 0.358f, wFrac = 0.552f, hFrac = 0.544f,
                    stageW = stageW, stageH = stageH,
                    description = "Profil Ibu Dosen Khotimah S.Pd., M.Pd",
                    testTag = "profil_card_dosen"
                )
            }

            SlideDestination.CAPAIAN,
            SlideDestination.TUJUAN -> {
                TopMenuAndNext(stageW, stageH, { onNavigate(SlideDestination.MENU) }, onNext)
            }

            SlideDestination.MATERI_MENU -> {
                TopMenuAndNext(stageW, stageH, { onNavigate(SlideDestination.MENU) }, onNext)
                // 5 golden pill topics (turn white on hover/press/select)
                val topics = listOf(
                    Triple(0.17f, "Mengenal Wirausaha", SlideDestination.MATERI_WIRAUSAHA),
                    Triple(0.335f, "Sikap Wirausahawan", SlideDestination.MATERI_SIKAP),
                    Triple(0.505f, "Kosakata dan Idiom", SlideDestination.MATERI_KOSAKATA),
                    Triple(0.67f, "Ide Pokok dan Teks Informatif", SlideDestination.MATERI_IDE_POKOK),
                    Triple(0.835f, "Wawancara dan Laporan", SlideDestination.MATERI_WAWANCARA)
                )
                topics.forEachIndexed { idx, (yPos, label, dest) ->
                    MenuPillWhiteHotspot(
                        xFrac = 0.295f, yFrac = yPos, wFrac = 0.415f, hFrac = 0.155f,
                        stageW = stageW, stageH = stageH,
                        index = idx,
                        label = label,
                        description = dest.title,
                        testTag = "materi_topic_${idx + 1}",
                        onSelect = { onNavigate(dest) }
                    )
                }
            }

            SlideDestination.MATERI_WIRAUSAHA,
            SlideDestination.MATERI_SIKAP,
            SlideDestination.MATERI_KOSAKATA,
            SlideDestination.TAHUKAH_KAMU,
            SlideDestination.KESIMPULAN -> {
                TopCornersBackNext(stageW, stageH, onBack, onNext)
            }

            SlideDestination.MATERI_IDE_POKOK -> {
                // Bottom-right Back & Next circles on Slide 14
                PulseHalo(0.70f, 0.76f, 0.135f, 0.22f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.842f, 0.76f, 0.135f, 0.22f, stageW, stageH, CircleShape, SunGold)
                HotspotOverlay(0.70f, 0.76f, 0.135f, 0.22f, stageW, stageH, CircleShape, "Kembali", "slide_back_button", onBack)
                HotspotOverlay(0.842f, 0.76f, 0.135f, 0.22f, stageW, stageH, CircleShape, "Lanjut", "slide_next_button", onNext)
            }

            SlideDestination.MATERI_WAWANCARA -> {
                // Bottom-left Back & Next circles on Slide 15
                PulseHalo(0.045f, 0.685f, 0.15f, 0.255f, stageW, stageH, CircleShape, SunGold)
                PulseHalo(0.205f, 0.685f, 0.15f, 0.255f, stageW, stageH, CircleShape, SunGold)
                HotspotOverlay(0.045f, 0.685f, 0.15f, 0.255f, stageW, stageH, CircleShape, "Kembali", "slide_back_button", onBack)
                HotspotOverlay(0.205f, 0.685f, 0.15f, 0.255f, stageW, stageH, CircleShape, "Lanjut", "slide_next_button", onNext)
            }

            SlideDestination.EVALUASI_START -> {
                PulseHalo(
                    xFrac = 0.068f, yFrac = 0.535f, wFrac = 0.405f, hFrac = 0.375f,
                    stageW = stageW, stageH = stageH,
                    shape = RoundedCornerShape(28.dp),
                    glowColor = SunGold
                )
                HotspotOverlay(
                    xFrac = 0.068f, yFrac = 0.535f, wFrac = 0.405f, hFrac = 0.375f,
                    stageW = stageW, stageH = stageH,
                    shape = RoundedCornerShape(28.dp),
                    description = "Mulai Evaluasi Pembelajaran",
                    testTag = "evaluasi_start_button",
                    onClick = { onNavigate(SlideDestination.SOAL_1) }
                )
            }

            SlideDestination.SOAL_1,
            SlideDestination.SOAL_2,
            SlideDestination.SOAL_3,
            SlideDestination.SOAL_4 -> {
                val qNum = slide.pageNumber - 18 // 1..4
                val question = DuniaUsahaContent.quizQuestions[qNum - 1]
                val chosenLetter = uiState.quizAnswers[qNum]

                val optionYPositions = listOf(
                    "A" to 0.16f,
                    "B" to 0.342f,
                    "C" to 0.525f,
                    "D" to 0.705f
                )

                optionYPositions.forEach { (letter, yFrac) ->
                    val isChosen = chosenLetter == letter
                    val isCorrect = letter == question.correctLetter
                    val highlightColor = when {
                        chosenLetter == null -> Color.Transparent
                        isChosen && isCorrect -> MeadowGreen
                        isChosen && !isCorrect -> CoralPinkDeep
                        isCorrect -> MeadowGreen.copy(alpha = 0.65f)
                        else -> Color.Transparent
                    }

                    HotspotOverlay(
                        xFrac = 0.555f, yFrac = yFrac, wFrac = 0.395f, hFrac = 0.165f,
                        stageW = stageW, stageH = stageH,
                        shape = RoundedCornerShape(50),
                        borderColor = highlightColor,
                        fillColor = if (isChosen) highlightColor.copy(alpha = 0.16f) else Color.Transparent,
                        description = "Pilihan $letter",
                        testTag = "quiz_${qNum}_option_$letter",
                        onClick = {
                            if (letter == question.correctLetter) onPlaySuccess() else onPlayError()
                            onAnswerQuiz(qNum, letter)
                        }
                    )

                    if (chosenLetter != null && (isChosen || isCorrect)) {
                        Icon(
                            imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = if (isCorrect) "Benar" else "Kurang tepat",
                            tint = if (isCorrect) MeadowGreen else CoralPinkDeep,
                            modifier = Modifier
                                .offset(x = stageW * 0.91f, y = stageH * (yFrac + 0.055f))
                                .size(26.dp)
                        )
                    }
                }

                // Floating compact feedback pill at bottom-center when answered
                AnimatedVisibility(
                    visible = chosenLetter != null,
                    enter = fadeIn() + slideInVertically { it / 2 },
                    exit = fadeOut(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = stageH * 0.025f)
                ) {
                    val isRight = chosenLetter == question.correctLetter
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (isRight) MeadowMint else RoseSoft,
                        border = BorderStroke(2.dp, if (isRight) MeadowGreen else CoralPinkDeep),
                        shadowElevation = 6.dp
                    ) {
                        Text(
                            text = if (isRight) "✓ Benar! ${question.explanation}" else "Jawaban tepat: ${question.correctLetter}. ${question.explanation}",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                            color = InkNavy,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp)
                        )
                    }
                }

                // KEMBALI and SELANJUTNYA buttons
                HotspotOverlay(
                    xFrac = 0.022f, yFrac = 0.885f, wFrac = 0.195f, hFrac = 0.088f,
                    stageW = stageW, stageH = stageH,
                    shape = RoundedCornerShape(16.dp),
                    description = "Kembali",
                    testTag = "quiz_back_button",
                    onClick = onBack
                )
                HotspotOverlay(
                    xFrac = 0.772f, yFrac = 0.885f, wFrac = 0.205f, hFrac = 0.088f,
                    stageW = stageW, stageH = stageH,
                    shape = RoundedCornerShape(16.dp),
                    description = "Selanjutnya",
                    testTag = "quiz_next_button",
                    onClick = onNext
                )
            }

            SlideDestination.EVALUASI_HASIL -> {
                CelebrationConfettiLayer()
                // Floating score & action strip at bottom center
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MeadowGreen,
                        border = BorderStroke(2.dp, Color.White),
                        shadowElevation = 5.dp
                    ) {
                        Text(
                            text = "Skor Kamu: ${uiState.quizCorrectCount}/4 (${uiState.quizScorePercent} Poin)",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                    Button(
                        onClick = onResetQuiz,
                        colors = ButtonDefaults.buttonColors(containerColor = TealButton),
                        modifier = Modifier
                            .height(40.dp)
                            .testTag("evaluasi_retry_button")
                    ) {
                        Icon(Icons.Default.Refresh, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ulangi", fontSize = 12.sp)
                    }
                    Button(
                        onClick = onNext,
                        colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                        modifier = Modifier
                            .height(40.dp)
                            .testTag("evaluasi_next_button")
                    ) {
                        Text("Lanjut →", fontSize = 12.sp)
                    }
                }
            }

            SlideDestination.REFLEKSI -> {
                val cards = listOf(
                    Triple(AppRating.SERU, 0.132f, 0.24f),
                    Triple(AppRating.KURANG, 0.38f, 0.24f),
                    Triple(AppRating.TIDAK_BAGUS, 0.632f, 0.24f)
                )
                cards.forEach { (rating, xFrac, wFrac) ->
                    val isChosen = uiState.selectedRating == rating
                    HotspotOverlay(
                        xFrac = xFrac, yFrac = 0.355f, wFrac = wFrac, hFrac = 0.545f,
                        stageW = stageW, stageH = stageH,
                        shape = RoundedCornerShape(18.dp),
                        borderColor = if (isChosen) MeadowGreen else Color.Transparent,
                        fillColor = if (isChosen) SunGold.copy(alpha = 0.14f) else Color.Transparent,
                        description = "Pilih penilaian ${rating.label}",
                        testTag = "rating_${rating.name.lowercase()}",
                        onClick = { onSelectRating(rating) }
                    )
                }
                AnimatedVisibility(
                    visible = uiState.selectedRating != null,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut(),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = InkNavy,
                        shadowElevation = 6.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Terima kasih telah memilih \"${uiState.selectedRating?.label}\"!",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                            Surface(
                                onClick = onNext,
                                shape = RoundedCornerShape(50),
                                color = SunAmber
                            ) {
                                Text(
                                    text = "Lanjut →",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = InkNavy,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            SlideDestination.SCAN_QR -> {
                // Fit QR code seamlessly inside the dashed "Tempel QR Code di sini" box
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 0.dp,
                    modifier = Modifier
                        .offset(x = stageW * 0.367f, y = stageH * 0.323f)
                        .size(width = stageW * 0.266f, height = stageH * 0.382f)
                        .clickable {
                            onPlayClick()
                            onToggleQrDialog(true)
                        }
                        .testTag("qr_code_box")
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.qr_code_custom),
                            contentDescription = "QR Code Kegiatan Dunia Usaha",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                QrScannerSweepOverlay(
                    xFrac = 0.367f,
                    yFrac = 0.323f,
                    wFrac = 0.266f,
                    hFrac = 0.382f,
                    stageW = stageW,
                    stageH = stageH
                )
                Surface(
                    onClick = { onToggleQrDialog(true) },
                    shape = RoundedCornerShape(50),
                    color = SkyBlue,
                    shadowElevation = 5.dp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 28.dp, bottom = 18.dp)
                        .testTag("open_qr_mission_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Default.QrCode2, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Text("Lihat Misi QR", style = MaterialTheme.typography.labelLarge, color = Color.White)
                    }
                }

                if (uiState.showQrMissionDialog) {
                    AlertDialog(
                        onDismissRequest = { onToggleQrDialog(false) },
                        title = { Text("Misi Pengusaha Cilik (Kegiatan Lanjutan)") },
                        text = {
                            Text(
                                "1. Bentuk kelompok kecil bersama temanmu.\n" +
                                    "2. Lakukan wawancara singkat dengan 1 pedagang di sekitar sekolah atau rumah menggunakan 7 pertanyaan wawancara.\n" +
                                    "3. Susun laporan hasil wawancara dan presentasikan dengan bahasa yang santun!"
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = { onToggleQrDialog(false) }) {
                                Text("Siap Laksanakan!")
                            }
                        }
                    )
                }
            }

            SlideDestination.DAFTAR_PUSTAKA -> {
                Surface(
                    onClick = { onNavigate(SlideDestination.COVER) },
                    shape = RoundedCornerShape(50),
                    color = SunAmber,
                    border = BorderStroke(2.dp, InkNavy),
                    shadowElevation = 5.dp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 28.dp, bottom = 18.dp)
                        .testTag("daftar_pustaka_home_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Icon(Icons.Default.Home, null, tint = InkNavy, modifier = Modifier.size(18.dp))
                        Text("Kembali ke Sampul", style = MaterialTheme.typography.labelLarge, color = InkNavy)
                    }
                }
            }

            SlideDestination.GAME_ARENA -> Unit
        }
    }
}

@Composable
private fun TopCornersBackNext(
    stageW: Dp,
    stageH: Dp,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    HotspotOverlay(
        xFrac = 0.015f, yFrac = 0.025f, wFrac = 0.135f, hFrac = 0.225f,
        stageW = stageW, stageH = stageH,
        shape = CircleShape,
        description = "Halaman Sebelumnya",
        testTag = "slide_back_button",
        onClick = onBack
    )
    HotspotOverlay(
        xFrac = 0.85f, yFrac = 0.025f, wFrac = 0.135f, hFrac = 0.225f,
        stageW = stageW, stageH = stageH,
        shape = CircleShape,
        description = "Halaman Berikutnya",
        testTag = "slide_next_button",
        onClick = onNext
    )
}

@Composable
private fun TopMenuAndNext(
    stageW: Dp,
    stageH: Dp,
    onMenu: () -> Unit,
    onNext: () -> Unit
) {
    HotspotOverlay(
        xFrac = 0.015f, yFrac = 0.025f, wFrac = 0.125f, hFrac = 0.21f,
        stageW = stageW, stageH = stageH,
        shape = CircleShape,
        description = "Menu Utama",
        testTag = "slide_menu_button",
        onClick = onMenu
    )
    HotspotOverlay(
        xFrac = 0.855f, yFrac = 0.025f, wFrac = 0.13f, hFrac = 0.21f,
        stageW = stageW, stageH = stageH,
        shape = CircleShape,
        description = "Halaman Berikutnya",
        testTag = "slide_next_button",
        onClick = onNext
    )
}

@Composable
private fun PulseHalo(
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp,
    shape: Shape,
    glowColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "haloPulse")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "haloScale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "haloAlpha"
    )

    Box(
        modifier = Modifier
            .offset(x = stageW * xFrac, y = stageH * yFrac)
            .size(width = stageW * wFrac, height = stageH * hFrac)
            .graphicsLayer {
                scaleX = pulse
                scaleY = pulse
            }
            .border(4.dp, glowColor.copy(alpha = alpha), shape)
    )
}

@Composable
private fun HotspotOverlay(
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp,
    shape: Shape,
    description: String,
    testTag: String,
    onClick: () -> Unit,
    borderColor: Color = Color.Transparent,
    fillColor: Color = Color.Transparent
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "hotspotScale"
    )

    Box(
        modifier = Modifier
            .offset(x = stageW * xFrac, y = stageH * yFrac)
            .size(width = stageW * wFrac, height = stageH * hFrac)
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
            }
            .clip(shape)
            .background(fillColor)
            .then(
                if (borderColor != Color.Transparent) {
                    Modifier.border(3.5.dp, borderColor, shape)
                } else {
                    Modifier
                }
            )
            .semantics { contentDescription = description }
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material3.ripple(color = Color.White),
                role = Role.Button,
                onClick = onClick
            )
            .testTag(testTag)
    )
}

@Composable
private fun PlayButtonAnimatedOverlay(
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp,
    onStart: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "playAnimations")
    val rippleScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.38f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "playRippleScale"
    )
    val rippleAlpha by infiniteTransition.animateFloat(
        initialValue = 0.75f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "playRippleAlpha"
    )
    val breatheScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "playBreatheScale"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    var isLaunching by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val clickScale by animateFloatAsState(
        targetValue = when {
            isPressed || isLaunching -> 0.88f
            isHovered -> 1.1f
            else -> 1f
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "playClickScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(x = stageW * xFrac, y = stageH * yFrac)
            .size(width = stageW * wFrac, height = stageH * hFrac)
    ) {
        // Expanding golden-white sonar ripple ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = rippleScale
                    scaleY = rippleScale
                }
                .border(4.dp, Color.White.copy(alpha = rippleAlpha), CircleShape)
        )
        // Secondary warm gold breathing halo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = breatheScale * 1.06f
                    scaleY = breatheScale * 1.06f
                }
                .border(4.dp, SunGold.copy(alpha = 0.65f), CircleShape)
        )
        // Interactive clickable Play circle
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = breatheScale * clickScale
                    scaleY = breatheScale * clickScale
                }
                .clip(CircleShape)
                .background(
                    when {
                        isLaunching || isPressed -> Color.White.copy(alpha = 0.42f)
                        isHovered -> Color.White.copy(alpha = 0.22f)
                        else -> Color.Transparent
                    }
                )
                .hoverable(interactionSource = interactionSource)
                .semantics { contentDescription = "Mulai Belajar Dunia Usaha" }
                .clickable(
                    interactionSource = interactionSource,
                    indication = androidx.compose.material3.ripple(color = Color.White),
                    role = Role.Button,
                    onClick = {
                        if (!isLaunching) {
                            isLaunching = true
                            scope.launch {
                                delay(160)
                                isLaunching = false
                                onStart()
                            }
                        }
                    }
                )
                .testTag("cover_play_button")
        )
    }
}

@Composable
private fun MenuPillWhiteHotspot(
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp,
    index: Int = 0,
    label: String,
    description: String,
    testTag: String,
    onSelect: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()
    var isSelecting by remember { mutableStateOf(false) }
    var entrancePulse by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(testTag) {
        delay(index * 75L)
        entrancePulse = true
        delay(240L)
        entrancePulse = false
    }

    val isHighlighted = isPressed || isHovered || isSelecting
    val bgColor by animateColorAsState(
        targetValue = when {
            isHighlighted -> Color.White
            entrancePulse -> Color.White.copy(alpha = 0.45f)
            else -> Color.Transparent
        },
        animationSpec = tween(durationMillis = 160),
        label = "menuPillWhiteBg"
    )
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed || isSelecting -> 0.95f
            isHovered || entrancePulse -> 1.04f
            else -> 1f
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "menuPillScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(x = stageW * xFrac, y = stageH * yFrac)
            .size(width = stageW * wFrac, height = stageH * hFrac)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .then(
                if (isHighlighted || entrancePulse) {
                    Modifier.border(
                        width = if (isHighlighted) 2.5.dp else 1.5.dp,
                        color = if (isHighlighted) InkNavy else Color.White.copy(alpha = 0.85f),
                        shape = RoundedCornerShape(50)
                    )
                } else {
                    Modifier
                }
            )
            .hoverable(interactionSource = interactionSource)
            .semantics { contentDescription = description }
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material3.ripple(color = SunGold),
                role = Role.Button,
                onClick = {
                    if (!isSelecting) {
                        isSelecting = true
                        scope.launch {
                            delay(150)
                            isSelecting = false
                            onSelect()
                        }
                    }
                }
            )
            .testTag(testTag)
    ) {
        if (isHighlighted) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = com.example.ui.theme.FredokaFontFamily,
                    fontSize = (stageH.value * 0.052f).coerceIn(14f, 26f).sp,
                    fontWeight = FontWeight.Bold
                ),
                color = InkNavy,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}



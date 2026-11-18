package com.example.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.audio.SoundManager
import com.example.model.SlideDestination
import com.example.model.TransitionStyle
import com.example.ui.components.ExactSlideStage
import com.example.ui.screens.MiniGameScreen
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCard
import com.example.ui.theme.InkNavy
import com.example.ui.theme.SunGold
import com.example.ui.theme.TealButton
import com.example.viewmodel.PresentationViewModel

@Composable
fun DuniaUsahaApp(
    viewModel: PresentationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    var swipeAccumulator by remember { mutableFloatStateOf(0f) }
    val soundManager = remember { SoundManager() }
    val lifecycleOwner = LocalLifecycleOwner.current

    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            soundManager.loadCustomAudio(context, uri) { loadedName ->
                if (loadedName != null) {
                    viewModel.setCustomAudioName(loadedName)
                    if (!uiState.isMusicEnabled) {
                        viewModel.toggleMusic()
                    }
                    viewModel.setAudioDialogVisible(false)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        soundManager.initializeAndStart(context)
        val savedName = soundManager.getSavedCustomAudioName(context)
        if (savedName != null) {
            viewModel.setCustomAudioName(savedName)
        }
    }

    LaunchedEffect(uiState.isMusicEnabled) {
        soundManager.setMusicEnabled(uiState.isMusicEnabled)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> soundManager.onLifecyclePause()
                Lifecycle.Event.ON_RESUME -> soundManager.onLifecycleResume()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            soundManager.release()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .focusRequester(focusRequester)
            .focusable()
            .onPreviewKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.DirectionRight, Key.Spacebar, Key.PageDown -> {
                            viewModel.nextSlide()
                            true
                        }
                        Key.DirectionLeft, Key.PageUp -> {
                            viewModel.previousSlide()
                            true
                        }
                        Key.Home -> {
                            viewModel.navigateTo(SlideDestination.COVER)
                            true
                        }
                        else -> false
                    }
                } else {
                    false
                }
            }
            .pointerInput(uiState.currentSlide) {
                detectHorizontalDragGestures(
                    onDragStart = { swipeAccumulator = 0f },
                    onHorizontalDrag = { _, dragAmount -> swipeAccumulator += dragAmount },
                    onDragEnd = {
                        if (swipeAccumulator < -90f) {
                            viewModel.nextSlide()
                        } else if (swipeAccumulator > 90f) {
                            viewModel.previousSlide()
                        }
                        swipeAccumulator = 0f
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        val stageModifier = if (maxWidth / maxHeight > 16f / 9f) {
            Modifier
                .fillMaxHeight()
                .aspectRatio(16f / 9f)
        } else {
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        }

        Box(modifier = stageModifier) {
            AnimatedContent(
                targetState = uiState.currentSlide,
                transitionSpec = {
                    slideTransitionSpec(
                        style = uiState.transitionStyle,
                        isForward = uiState.isForward
                    )
                },
                modifier = Modifier.fillMaxSize(),
                label = "desktopCleanSlideTransition"
            ) { slide ->
                if (slide == SlideDestination.GAME_ARENA) {
                    MiniGameScreen(
                        uiState = uiState,
                        onSelectMode = { mode ->
                            soundManager.playClick()
                            viewModel.setGameMode(mode)
                        },
                        onAnswerIdiom = { choice ->
                            soundManager.playClick()
                            viewModel.answerIdiomGame(choice)
                        },
                        onNextIdiom = {
                            soundManager.playClick()
                            viewModel.nextIdiomRound()
                        },
                        onChooseWarung = { opt ->
                            soundManager.playClick()
                            viewModel.chooseWarungAction(opt)
                        },
                        onResetWarung = {
                            soundManager.playClick()
                            viewModel.resetWarungGame()
                        },
                        onBackToMenu = {
                            soundManager.playClick()
                            viewModel.navigateTo(SlideDestination.MENU)
                        }
                    )
                } else {
                    ExactSlideStage(
                        slide = slide,
                        uiState = uiState,
                        onNext = {
                            soundManager.playClick()
                            viewModel.nextSlide()
                        },
                        onBack = {
                            soundManager.playClick()
                            viewModel.previousSlide()
                        },
                        onNavigate = { dest ->
                            soundManager.playClick()
                            viewModel.navigateTo(dest)
                        },
                        onAnswerQuiz = viewModel::answerQuiz,
                        onResetQuiz = {
                            soundManager.playClick()
                            viewModel.resetQuiz()
                        },
                        onSelectRating = { rating ->
                            soundManager.playSuccess()
                            viewModel.selectAppRating(rating)
                        },
                        onToggleQrDialog = viewModel::setQrMissionDialogVisible,
                        onPlayClick = soundManager::playClick,
                        onPlaySuccess = soundManager::playSuccess,
                        onPlayError = soundManager::playError,
                        onPlayFanfare = soundManager::playFanfare
                    )
                }
            }

        }

        // Compact floating backsound control in the top-left corner (pojok bagian kiri atas)
        Surface(
            shape = RoundedCornerShape(50),
            color = if (uiState.isMusicEnabled) SunGold.copy(alpha = 0.94f) else CreamCard.copy(alpha = 0.92f),
            border = BorderStroke(1.5.dp, InkNavy.copy(alpha = 0.78f)),
            shadowElevation = 4.dp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 6.dp, top = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = 42.dp, height = 48.dp)
                        .semantics {
                            contentDescription = if (uiState.isMusicEnabled) {
                                "Matikan Backsound"
                            } else {
                                "Nyalakan Backsound"
                            }
                        }
                        .clickable(
                            role = Role.Button,
                            onClick = viewModel::toggleMusic
                        )
                        .testTag("toggle_backsound_button")
                ) {
                    Icon(
                        imageVector = if (uiState.isMusicEnabled) {
                            Icons.AutoMirrored.Filled.VolumeUp
                        } else {
                            Icons.AutoMirrored.Filled.VolumeOff
                        },
                        contentDescription = null,
                        tint = InkNavy,
                        modifier = Modifier.size(20.dp)
                    )
                }

                VerticalDivider(
                    color = InkNavy.copy(alpha = 0.28f),
                    modifier = Modifier.height(22.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(width = 40.dp, height = 48.dp)
                        .semantics { contentDescription = "Pengaturan Sumber Backsound" }
                        .clickable(
                            role = Role.Button,
                            onClick = {
                                soundManager.playClick()
                                viewModel.setAudioDialogVisible(true)
                            }
                        )
                        .testTag("audio_settings_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = InkNavy,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }
        }

        if (uiState.showAudioDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.setAudioDialogVisible(false) },
                title = {
                    Text(
                        text = "Pengaturan Backsound",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = if (uiState.customAudioName != null) {
                                "Musik aktif: ${uiState.customAudioName}"
                            } else {
                                "Musik aktif: Musik Ceria Anak-Anak (Terpasang)"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Button(
                            onClick = { audioPickerLauncher.launch(arrayOf("audio/*")) },
                            colors = ButtonDefaults.buttonColors(containerColor = TealButton),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("pick_custom_audio_button")
                        ) {
                            Icon(Icons.Default.AudioFile, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Pilih File Audio Lain (MP3/WAV)")
                        }
                        if (uiState.customAudioName != null) {
                            OutlinedButton(
                                onClick = {
                                    soundManager.clearCustomAudio(context)
                                    viewModel.setCustomAudioName(null)
                                    viewModel.setAudioDialogVisible(false)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("reset_default_audio_button")
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Gunakan Backsound Utama")
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.setAudioDialogVisible(false) }) {
                        Text("Tutup")
                    }
                }
            )
        }
    }
}

private fun slideTransitionSpec(
    style: TransitionStyle,
    isForward: Boolean
): ContentTransform {
    return when (style) {
        TransitionStyle.SLIDE_SCALE -> {
            val dir = if (isForward) 1 else -1
            (slideInHorizontally(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ),
                initialOffsetX = { fullWidth -> dir * (fullWidth * 0.45f).toInt() }
            ) + scaleIn(
                initialScale = 0.94f,
                animationSpec = tween(360)
            ) + fadeIn(animationSpec = tween(300))) togetherWith
                (slideOutHorizontally(
                    animationSpec = tween(320),
                    targetOffsetX = { fullWidth -> -dir * (fullWidth * 0.3f).toInt() }
                ) + scaleOut(
                    targetScale = 0.96f,
                    animationSpec = tween(320)
                ) + fadeOut(animationSpec = tween(240)))
        }
        TransitionStyle.FADE_ZOOM -> {
            (scaleIn(
                initialScale = if (isForward) 0.88f else 1.08f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            ) + fadeIn(animationSpec = tween(340))) togetherWith
                (scaleOut(
                    targetScale = if (isForward) 1.06f else 0.90f,
                    animationSpec = tween(280)
                ) + fadeOut(animationSpec = tween(260)))
        }
        TransitionStyle.BOOK_FLIP -> {
            val dir = if (isForward) 1 else -1
            (slideInHorizontally(
                animationSpec = tween(420),
                initialOffsetX = { fullWidth -> dir * fullWidth }
            ) + fadeIn(animationSpec = tween(320))) togetherWith
                (slideOutHorizontally(
                    animationSpec = tween(420),
                    targetOffsetX = { fullWidth -> -dir * (fullWidth / 3) }
                ) + scaleOut(targetScale = 0.9f, animationSpec = tween(420)) + fadeOut(animationSpec = tween(280)))
        }
    }
}

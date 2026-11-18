package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppRating
import com.example.model.DuniaUsahaContent
import com.example.model.QuizQuestion
import com.example.ui.components.BoardGameTrackBackdrop
import com.example.ui.components.CelebrationConfettiLayer
import com.example.ui.components.CuteKidBookIllustration
import com.example.ui.components.EvaluasiPropsIllustration
import com.example.ui.components.LayeredDisplayTitle
import com.example.ui.components.QrCodeMatrixBox
import com.example.ui.components.RatingFaceIllustration
import com.example.ui.components.SlideTopNavRow
import com.example.ui.components.SoftMarketBackdrop
import com.example.ui.components.StaggeredEntrance
import com.example.ui.theme.CoralButton
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CoralPinkDeep
import com.example.ui.theme.CreamCard
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.InkNavy
import com.example.ui.theme.MeadowGreen
import com.example.ui.theme.MeadowMint
import com.example.ui.theme.MeadowOutline
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import com.example.ui.theme.TealButton

@Composable
fun Slide18EvaluasiStart(
    onStartQuiz: () -> Unit,
    onBack: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "startBounce")
    val startScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(950, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "startPulse"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        BoardGameTrackBackdrop()

        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = false,
                onBack = onBack
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 36.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StaggeredEntrance(delayMillis = 60) {
                        LayeredDisplayTitle(
                            text = "EVALUASI\nPEMBELAJARAN",
                            fillColor = SunGold,
                            shadowColor = InkNavy.copy(alpha = 0.35f),
                            fontSize = 38.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                    StaggeredEntrance(delayMillis = 130) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = CoralButton,
                            border = BorderStroke(2.dp, Color.White),
                            shadowElevation = 4.dp
                        ) {
                            Text(
                                text = "Materi \"Sukses Berwirausaha\"",
                                style = MaterialTheme.typography.titleMedium.copy(fontFamily = FredokaFontFamily),
                                color = SunGold,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                            )
                        }
                    }
                    StaggeredEntrance(delayMillis = 200) {
                        Surface(
                            onClick = onStartQuiz,
                            shape = RoundedCornerShape(22.dp),
                            color = SunAmber,
                            border = BorderStroke(4.dp, Color(0xFFFFF3C4)),
                            shadowElevation = 10.dp,
                            modifier = Modifier
                                .width(260.dp)
                                .height(86.dp)
                                .minimumInteractiveComponentSize()
                                .graphicsLayer {
                                    scaleX = startScale
                                    scaleY = startScale
                                }
                                .testTag("evaluasi_start_button")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "START",
                                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 42.sp),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                StaggeredEntrance(
                    delayMillis = 120,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    EvaluasiPropsIllustration(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SlideQuizQuestion(
    question: QuizQuestion,
    selectedLetter: String?,
    onSelectOption: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val accentBorder = when (question.number) {
        1 -> SunGold
        2 -> MeadowGreen
        3 -> SunAmber
        else -> SkyBlue
    }
    val optionBadgeColors = mapOf(
        "A" to SkyBlue,
        "B" to SunAmber,
        "C" to MeadowGreen,
        "D" to CoralButton
    )

    Box(modifier = Modifier.fillMaxSize()) {
        BoardGameTrackBackdrop()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Question board card with tilted SOAL header
                StaggeredEntrance(delayMillis = 60, modifier = Modifier.weight(0.95f)) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = CreamCard,
                            border = BorderStroke(5.dp, accentBorder),
                            shadowElevation = 8.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 26.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 34.dp,
                                        bottom = 22.dp
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                if (question.readingText != null) {
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = SunGold.copy(alpha = 0.16f),
                                        border = BorderStroke(1.dp, SunAmber.copy(alpha = 0.4f))
                                    ) {
                                        Text(
                                            text = question.readingText,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                            color = InkNavy,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = question.question,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp
                                    ),
                                    color = InkNavy,
                                    textAlign = TextAlign.Center
                                )

                                AnimatedVisibility(visible = selectedLetter != null) {
                                    val isCorrect = selectedLetter == question.correctLetter
                                    Surface(
                                        shape = RoundedCornerShape(14.dp),
                                        color = if (isCorrect) MeadowMint else RoseSoft,
                                        border = BorderStroke(
                                            1.5.dp,
                                            if (isCorrect) MeadowGreen else CoralPinkDeep
                                        )
                                    ) {
                                        Text(
                                            text = if (isCorrect) {
                                                "✓ ${question.explanation}"
                                            } else {
                                                "Jawaban tepat adalah ${question.correctLetter}. ${question.explanation}"
                                            },
                                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                            color = InkNavy,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // SOAL X header badge
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = CoralButton,
                            border = BorderStroke(2.5.dp, Color.White),
                            shadowElevation = 6.dp
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Star, null, tint = SunGold, modifier = Modifier.size(22.dp))
                                Text(
                                    text = "SOAL ${question.number}",
                                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 26.sp),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Right: A, B, C, D options
                Column(
                    modifier = Modifier.weight(1.05f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    question.options.forEachIndexed { idx, opt ->
                        val isSelected = selectedLetter == opt.letter
                        val isCorrect = opt.letter == question.correctLetter
                        val badgeColor = optionBadgeColors[opt.letter] ?: SkyBlue
                        val bgColor = when {
                            selectedLetter == null -> CreamCard
                            isSelected && isCorrect -> MeadowMint
                            isSelected && !isCorrect -> RoseSoft
                            isCorrect -> MeadowMint.copy(alpha = 0.55f)
                            else -> CreamCard
                        }
                        val borderColor = when {
                            isSelected && isCorrect -> MeadowGreen
                            isSelected && !isCorrect -> CoralPinkDeep
                            isSelected -> badgeColor
                            else -> badgeColor.copy(alpha = 0.45f)
                        }
                        val optScale by animateFloatAsState(
                            targetValue = if (isSelected) 1.02f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            label = "optScale"
                        )

                        StaggeredEntrance(delayMillis = 90 + idx * 55) {
                            Surface(
                                onClick = { onSelectOption(opt.letter) },
                                shape = RoundedCornerShape(50),
                                color = bgColor,
                                border = BorderStroke(if (isSelected) 3.dp else 1.5.dp, borderColor),
                                shadowElevation = if (isSelected) 7.dp else 3.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .minimumInteractiveComponentSize()
                                    .graphicsLayer {
                                        scaleX = optScale
                                        scaleY = optScale
                                    }
                                    .testTag("quiz_${question.number}_option_${opt.letter}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = badgeColor,
                                        border = BorderStroke(2.dp, Color.White),
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = opt.letter,
                                                style = MaterialTheme.typography.headlineMedium,
                                                color = Color.White
                                            )
                                        }
                                    }
                                    Text(
                                        text = opt.text,
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.5.sp),
                                        color = InkNavy,
                                        modifier = Modifier.weight(1f)
                                    )
                                    if (selectedLetter != null && isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Jawaban Benar",
                                            tint = MeadowGreen,
                                            modifier = Modifier
                                                .padding(end = 6.dp)
                                                .size(22.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Bottom KEMBALI and SELANJUTNYA buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onBack,
                    shape = RoundedCornerShape(16.dp),
                    color = TealButton,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .height(48.dp)
                        .minimumInteractiveComponentSize()
                        .testTag("quiz_back_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Text("KEMBALI", style = MaterialTheme.typography.labelLarge, color = Color.White)
                    }
                }

                Surface(
                    onClick = onNext,
                    shape = RoundedCornerShape(16.dp),
                    color = CoralButton,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .height(48.dp)
                        .minimumInteractiveComponentSize()
                        .testTag("quiz_next_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        Text("SELANJUTNYA", style = MaterialTheme.typography.labelLarge, color = Color.White)
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun Slide23HebatHasil(
    correctCount: Int,
    totalCount: Int,
    scorePercent: Int,
    onRetry: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        BoardGameTrackBackdrop()
        CelebrationConfettiLayer()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            StaggeredEntrance(delayMillis = 40) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LayeredDisplayTitle(
                        text = "HEBAT!",
                        fillColor = SunGold,
                        shadowColor = InkNavy,
                        fontSize = 44.sp
                    )
                    Text(
                        text = "KAMU BERHASIL!",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 26.sp),
                        color = InkNavy
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MeadowGreen,
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Text(
                            text = "Skor Evaluasi: $correctCount / $totalCount Benar ($scorePercent Poin)",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CuteKidBookIllustration(
                    isGirl = false,
                    modifier = Modifier
                        .weight(0.55f)
                        .height(190.dp)
                )

                Column(
                    modifier = Modifier.weight(1.4f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StaggeredEntrance(delayMillis = 100) {
                        ResultBannerCard(
                            icon = Icons.Default.EmojiEvents,
                            iconColor = SunAmber,
                            bgColor = Color(0xFFFFF4D1),
                            borderColor = SunGold,
                            text = "Selamat, kamu sudah menyelesaikan tantangan evaluasi!"
                        )
                    }
                    StaggeredEntrance(delayMillis = 180) {
                        ResultBannerCard(
                            icon = Icons.Default.Lightbulb,
                            iconColor = MeadowGreen,
                            bgColor = MeadowMint,
                            borderColor = MeadowGreen,
                            text = "Terus belajar, tetap kreatif, dan jangan takut mencoba hal baru!"
                        )
                    }
                    StaggeredEntrance(delayMillis = 260) {
                        ResultBannerCard(
                            icon = Icons.Default.Favorite,
                            iconColor = CoralPinkDeep,
                            bgColor = RoseSoft,
                            borderColor = CoralPink,
                            text = "Kamu adalah calon pengusaha cilik yang luar biasa!"
                        )
                    }
                }

                CuteKidBookIllustration(
                    isGirl = true,
                    modifier = Modifier
                        .weight(0.55f)
                        .height(190.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(containerColor = TealButton),
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("evaluasi_retry_button")
                ) {
                    Icon(Icons.Default.Refresh, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Ulangi Kuis")
                }
                Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("evaluasi_next_button")
                ) {
                    Text("Lanjut ke Refleksi")
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
private fun ResultBannerCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    bgColor: Color,
    borderColor: Color,
    text: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(2.dp, borderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(26.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.5.sp),
                color = InkNavy
            )
        }
    }
}

@Composable
fun Slide24Refleksi(
    selectedRating: AppRating?,
    onSelectRating: (AppRating) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.24f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Surface(
                        shape = RoundedCornerShape(36.dp),
                        color = CreamCard,
                        border = BorderStroke(2.dp, InkNavy),
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "Bagaimana dengan Aplikasi Pembelajaran ini?",
                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp),
                            color = CoralPink,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp)
                        )
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 42.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val cardColors = mapOf(
                        AppRating.SERU to Pair(MeadowMint, MeadowOutline),
                        AppRating.KURANG to Pair(Color(0xFFFFF1C7), Color(0xFF7D4E00)),
                        AppRating.TIDAK_BAGUS to Pair(RoseSoft, Color(0xFFA93226))
                    )
                    AppRating.entries.forEachIndexed { idx, rating ->
                        val isChosen = selectedRating == rating
                        val (bg, textCol) = cardColors[rating]!!
                        val scale by animateFloatAsState(
                            targetValue = if (isChosen) 1.07f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                            label = "ratingScale"
                        )

                        StaggeredEntrance(delayMillis = 70 + idx * 70, modifier = Modifier.weight(1f)) {
                            Surface(
                                onClick = { onSelectRating(rating) },
                                shape = RoundedCornerShape(22.dp),
                                color = bg,
                                border = BorderStroke(
                                    width = if (isChosen) 4.dp else 1.5.dp,
                                    color = if (isChosen) InkNavy else textCol.copy(alpha = 0.3f)
                                ),
                                shadowElevation = if (isChosen) 10.dp else 4.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp)
                                    .minimumInteractiveComponentSize()
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                    .testTag("rating_${rating.name.lowercase()}")
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    RatingFaceIllustration(rating = rating)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = rating.label,
                                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 26.sp),
                                        color = textCol
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                AnimatedVisibility(visible = selectedRating != null) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = InkNavy,
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "Terima kasih telah memilih \"${selectedRating?.label}\"! Masukanmu sangat berarti.",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Slide25ScanQr(
    showMissionDialog: Boolean,
    onToggleMissionDialog: (Boolean) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.14f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = SunGold,
                        border = BorderStroke(2.5.dp, InkNavy),
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "Scan QR Code",
                            style = MaterialTheme.typography.headlineLarge,
                            color = InkNavy,
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp)
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CuteKidBookIllustration(
                    isGirl = false,
                    modifier = Modifier
                        .weight(0.55f)
                        .height(230.dp)
                )

                Surface(
                    shape = RoundedCornerShape(26.dp),
                    color = CreamCard,
                    border = BorderStroke(3.dp, InkNavy),
                    shadowElevation = 6.dp,
                    modifier = Modifier.weight(1.45f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Pindai QR Code berikut untuk melanjutkan kegiatan.",
                            style = MaterialTheme.typography.titleMedium,
                            color = InkNavy,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            QrCodeMatrixBox(
                                modifier = Modifier
                                    .size(144.dp)
                                    .testTag("qr_code_box")
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(shape = CircleShape, color = SunGold, modifier = Modifier.size(32.dp)) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.CameraAlt, null, tint = InkNavy, modifier = Modifier.size(18.dp))
                                        }
                                    }
                                    Text(
                                        text = "Arahkan kamera atau pemindai QR ke kode.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = InkNavy
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(shape = CircleShape, color = RoseSoft, modifier = Modifier.size(32.dp)) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.ListAlt, null, tint = InkNavy, modifier = Modifier.size(18.dp))
                                        }
                                    }
                                    Text(
                                        text = "Ikuti petunjuk yang muncul.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = InkNavy
                                    )
                                }
                                Button(
                                    onClick = { onToggleMissionDialog(true) },
                                    colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                                    modifier = Modifier
                                        .height(44.dp)
                                        .testTag("open_qr_mission_button")
                                ) {
                                    Icon(Icons.Default.QrCode2, null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Buka Petunjuk Kegiatan")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showMissionDialog) {
        AlertDialog(
            onDismissRequest = { onToggleMissionDialog(false) },
            title = { Text("Misi Pengusaha Cilik (Kegiatan Lanjutan)") },
            text = {
                Text(
                    "1. Bentuk kelompok kecil bersama temanmu.\n" +
                        "2. Lakukan wawancara singkat dengan 1 pedagang di sekitar sekolah atau rumah menggunakan 7 contoh pertanyaan wawancara.\n" +
                        "3. Susun laporan hasil wawancara dan presentasikan dengan bahasa yang santun di depan kelas!"
                )
            },
            confirmButton = {
                TextButton(onClick = { onToggleMissionDialog(false) }) {
                    Text("Siap Laksanakan!")
                }
            }
        )
    }
}

@Composable
fun Slide26DaftarPustaka(
    onBack: () -> Unit,
    onHome: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.14f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = false,
                onBack = onBack,
                centerContent = {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color(0xFFFFF5CC),
                        border = BorderStroke(2.5.dp, InkNavy),
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "DAFTAR PUSTAKA",
                            style = MaterialTheme.typography.headlineLarge,
                            color = InkNavy,
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 6.dp)
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CuteKidBookIllustration(
                    isGirl = false,
                    modifier = Modifier
                        .weight(0.45f)
                        .height(210.dp)
                )

                Surface(
                    shape = RoundedCornerShape(26.dp),
                    color = CreamCard,
                    border = BorderStroke(3.dp, InkNavy),
                    shadowElevation = 6.dp,
                    modifier = Modifier.weight(1.55f)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DuniaUsahaContent.bibliography.forEachIndexed { idx, ref ->
                            Text(
                                text = "${idx + 1}. $ref",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 15.5.sp,
                                    lineHeight = 22.sp
                                ),
                                color = InkNavy
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(
                            onClick = onHome,
                            colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                            modifier = Modifier
                                .align(Alignment.End)
                                .height(48.dp)
                                .testTag("daftar_pustaka_home_button")
                        ) {
                            Icon(Icons.Default.Home, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Kembali ke Sampul")
                        }
                    }
                }
            }
        }
    }
}

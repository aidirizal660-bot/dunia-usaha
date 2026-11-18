package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DuniaUsahaContent
import com.example.ui.components.LayeredDisplayTitle
import com.example.ui.components.SlideTopNavRow
import com.example.ui.components.SoftMarketBackdrop
import com.example.ui.components.StaggeredEntrance
import com.example.ui.theme.CoralPink
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

@Composable
fun MiniGameScreen(
    uiState: PresentationUiState,
    onSelectMode: (Int) -> Unit,
    onAnswerIdiom: (String) -> Unit,
    onNextIdiom: () -> Unit,
    onChooseWarung: (Int) -> Unit,
    onResetWarung: () -> Unit,
    onBackToMenu: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isCompactMobile = maxWidth < 560.dp

        SoftMarketBackdrop(alpha = 0.2f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = true,
                showBack = true,
                showNext = false,
                onMenu = onBackToMenu,
                onBack = onBackToMenu,
                centerContent = {
                    LayeredDisplayTitle(
                        text = "Games Pengusaha Cilik",
                        fillColor = CoralPink,
                        shadowColor = SunGold,
                        fontSize = if (isCompactMobile) 22.sp else 30.sp
                    )
                }
            )

            // Mode selector tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = if (isCompactMobile) 12.dp else 32.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameModeChip(
                    label = if (isCompactMobile) "Tebak Idiom" else "Tebak Idiom Usaha",
                    icon = Icons.Default.SportsEsports,
                    selected = uiState.gameModeIndex == 0,
                    testTag = "game_tab_idiom",
                    onClick = { onSelectMode(0) }
                )
                GameModeChip(
                    label = if (isCompactMobile) "Warung Cilik" else "Simulasi Warung Cilik",
                    icon = Icons.Default.Storefront,
                    selected = uiState.gameModeIndex == 1,
                    testTag = "game_tab_warung",
                    onClick = { onSelectMode(1) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        horizontal = if (isCompactMobile) 14.dp else 36.dp,
                        vertical = 6.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.gameModeIndex == 0) {
                    IdiomMiniGameCard(
                        isCompact = isCompactMobile,
                        roundIndex = uiState.idiomRoundIndex,
                        selectedChoice = uiState.idiomGameChoice,
                        score = uiState.idiomGameScore,
                        onAnswer = onAnswerIdiom,
                        onNextRound = onNextIdiom
                    )
                } else {
                    WarungSimulationCard(
                        isCompact = isCompactMobile,
                        capital = uiState.warungCapital,
                        step = uiState.warungStep,
                        feedback = uiState.warungFeedback,
                        stars = uiState.warungStars,
                        onChoose = onChooseWarung,
                        onReset = onResetWarung
                    )
                }
            }
        }
    }
}

@Composable
private fun GameModeChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = if (selected) InkNavy else CreamCard,
        border = BorderStroke(2.dp, InkNavy),
        shadowElevation = if (selected) 5.dp else 2.dp,
        modifier = Modifier
            .height(48.dp)
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 14.dp)
        ) {
            Icon(icon, null, tint = if (selected) SunGold else InkNavy, modifier = Modifier.size(18.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = if (selected) Color.White else InkNavy
            )
        }
    }
}

@Composable
private fun IdiomMiniGameCard(
    isCompact: Boolean,
    roundIndex: Int,
    selectedChoice: String?,
    score: Int,
    onAnswer: (String) -> Unit,
    onNextRound: () -> Unit
) {
    val round = DuniaUsahaContent.idiomGameRounds[roundIndex]
    StaggeredEntrance(delayMillis = 50) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CreamCard,
            border = BorderStroke(3.dp, InkNavy),
            shadowElevation = 6.dp,
            modifier = Modifier.fillMaxWidth(if (isCompact) 1f else 0.84f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(if (isCompact) 16.dp else 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = RoundedCornerShape(14.dp), color = SkyBlue) {
                        Text(
                            text = "Ronde ${roundIndex + 1} / ${DuniaUsahaContent.idiomGameRounds.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        )
                    }
                    Surface(shape = RoundedCornerShape(14.dp), color = SunGold) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Icon(Icons.Default.Star, null, tint = InkNavy, modifier = Modifier.size(16.dp))
                            Text(text = "Skor: $score", style = MaterialTheme.typography.labelLarge, color = InkNavy)
                        }
                    }
                }

                Text(
                    text = "Apa arti idiom \"${round.promptIdiom}\"?",
                    style = MaterialTheme.typography.headlineMedium,
                    color = CoralPinkDeep,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = round.sentenceHint,
                    style = MaterialTheme.typography.bodyLarge,
                    color = InkNavy,
                    textAlign = TextAlign.Center
                )

                if (isCompact) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        round.choices.forEachIndexed { idx, choice ->
                            IdiomChoiceButton(
                                choice = choice,
                                selectedChoice = selectedChoice,
                                correctMeaning = round.correctMeaning,
                                testTag = "idiom_game_choice_$idx",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                onClick = { onAnswer(choice) }
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        round.choices.forEachIndexed { idx, choice ->
                            IdiomChoiceButton(
                                choice = choice,
                                selectedChoice = selectedChoice,
                                correctMeaning = round.correctMeaning,
                                testTag = "idiom_game_choice_$idx",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(72.dp),
                                onClick = { onAnswer(choice) }
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = selectedChoice != null) {
                    val correct = selectedChoice == round.correctMeaning
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (correct) "Benar sekali! +20 Poin" else "Belum tepat! Jawabannya: ${round.correctMeaning}",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (correct) MeadowGreen else CoralPinkDeep,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = onNextRound,
                            colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                            modifier = Modifier
                                .height(48.dp)
                                .testTag("idiom_game_next")
                        ) {
                            Text(
                                if (roundIndex == DuniaUsahaContent.idiomGameRounds.lastIndex) "Main Lagi" else "Lanjut"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IdiomChoiceButton(
    choice: String,
    selectedChoice: String?,
    correctMeaning: String,
    testTag: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val isChosen = selectedChoice == choice
    val isCorrect = choice == correctMeaning
    val cardBg = when {
        selectedChoice == null -> Color(0xFFFFF8E8)
        isChosen && isCorrect -> MeadowMint
        isChosen && !isCorrect -> RoseSoft
        isCorrect -> MeadowMint.copy(alpha = 0.6f)
        else -> CreamCard
    }
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = cardBg,
        border = BorderStroke(2.dp, if (isChosen) InkNavy else SunAmber),
        modifier = modifier
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(10.dp)) {
            Text(
                text = choice,
                style = MaterialTheme.typography.titleMedium,
                color = InkNavy,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun WarungSimulationCard(
    isCompact: Boolean,
    capital: Int,
    step: Int,
    feedback: String?,
    stars: Int,
    onChoose: (Int) -> Unit,
    onReset: () -> Unit
) {
    StaggeredEntrance(delayMillis = 50) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CreamCard,
            border = BorderStroke(3.dp, InkNavy),
            shadowElevation = 6.dp,
            modifier = Modifier.fillMaxWidth(if (isCompact) 1f else 0.86f)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(if (isCompact) 16.dp else 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = RoundedCornerShape(14.dp), color = MeadowGreen) {
                        Text(
                            text = "Kas: Rp$capital",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    Surface(shape = RoundedCornerShape(14.dp), color = SunGold) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.EmojiEvents, null, tint = InkNavy, modifier = Modifier.size(18.dp))
                            Text(text = "Bintang: $stars/3", style = MaterialTheme.typography.labelLarge, color = InkNavy)
                        }
                    }
                }

                if (feedback != null) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MeadowMint,
                        border = BorderStroke(1.5.dp, MeadowGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = feedback,
                            style = MaterialTheme.typography.bodyMedium,
                            color = InkNavy,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                when (step) {
                    0 -> WarungDecisionBlock(
                        isCompact = isCompact,
                        prompt = "Langkah 1: Kamu punya modal Rp20.000. Apa tindakan pertamamu?",
                        optionA = "Beli bahan kue klepon dan jual di bazar sekolah",
                        optionB = "Habiskan seluruh modal untuk membeli mainan",
                        onChoose = onChoose
                    )
                    1 -> WarungDecisionBlock(
                        isCompact = isCompact,
                        prompt = "Langkah 2: Pembeli membayar Rp50.000 untuk belanja Rp35.000. Bagaimana sikapmu?",
                        optionA = "Mengembalikan Rp15.000 dengan senyum ramah dan jujur",
                        optionB = "Hanya mengembalikan Rp10.000 agar untung lebih banyak",
                        onChoose = onChoose
                    )
                    2 -> WarungDecisionBlock(
                        isCompact = isCompact,
                        prompt = "Langkah 3: Agar jualanmu makin dikenal teman-teman, apa yang kamu lakukan?",
                        optionA = "Membuat poster promosi menarik dan menjaga kebersihan kue",
                        optionB = "Berdiam diri saja tanpa merapikan lapak",
                        onChoose = onChoose
                    )
                    else -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Simulasi Selesai! Total Kasmu: Rp$capital ($stars Bintang)",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MeadowGreen,
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = onReset,
                                colors = ButtonDefaults.buttonColors(containerColor = TealButton),
                                modifier = Modifier
                                    .height(48.dp)
                                    .testTag("warung_reset_button")
                            ) {
                                Icon(Icons.Default.Refresh, null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Ulangi Simulasi Warung")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WarungDecisionBlock(
    isCompact: Boolean,
    prompt: String,
    optionA: String,
    optionB: String,
    onChoose: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = prompt,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = if (isCompact) 17.sp else 20.sp),
            color = InkNavy,
            textAlign = TextAlign.Center
        )
        if (isCompact) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WarungOptionButton(optionA, SunGold.copy(alpha = 0.25f), SunAmber, "warung_option_0", Modifier.fillMaxWidth()) {
                    onChoose(0)
                }
                WarungOptionButton(optionB, RoseSoft.copy(alpha = 0.5f), CoralPink, "warung_option_1", Modifier.fillMaxWidth()) {
                    onChoose(1)
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                WarungOptionButton(optionA, SunGold.copy(alpha = 0.25f), SunAmber, "warung_option_0", Modifier.weight(1f)) {
                    onChoose(0)
                }
                WarungOptionButton(optionB, RoseSoft.copy(alpha = 0.5f), CoralPink, "warung_option_1", Modifier.weight(1f)) {
                    onChoose(1)
                }
            }
        }
    }
}

@Composable
private fun WarungOptionButton(
    text: String,
    bgColor: Color,
    borderColor: Color,
    testTag: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = bgColor,
        border = BorderStroke(2.dp, borderColor),
        modifier = modifier
            .height(74.dp)
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp)) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge, color = InkNavy, textAlign = TextAlign.Center)
        }
    }
}

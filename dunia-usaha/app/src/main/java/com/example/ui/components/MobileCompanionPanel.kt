package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppRating
import com.example.model.DuniaUsahaContent
import com.example.model.SlideDestination
import com.example.model.TransitionStyle
import com.example.ui.theme.CoralButton
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CoralPinkDeep
import com.example.ui.theme.CreamCard
import com.example.ui.theme.CreamWarm
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.InkMuted
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
fun MobileTopBar(
    currentSlide: SlideDestination,
    transitionStyle: TransitionStyle,
    onSelectTransition: (TransitionStyle) -> Unit,
    onToggleOverview: () -> Unit,
    onToggleFullscreen: () -> Unit
) {
    var showTransitionMenu by remember { mutableStateOf(false) }

    Surface(
        color = CreamCard,
        shadowElevation = 3.dp,
        border = BorderStroke(1.dp, InkNavy.copy(alpha = 0.08f)),
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SunAmber,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = "Dunia Usaha",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Column {
                    Text(
                        text = "Dunia Usaha",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FredokaFontFamily,
                            fontSize = 16.sp
                        ),
                        color = InkNavy,
                        maxLines = 1
                    )
                    Text(
                        text = if (currentSlide == SlideDestination.GAME_ARENA) {
                            "Games Pengusaha Cilik"
                        } else {
                            "Hal ${currentSlide.pageNumber}/26 • ${currentSlide.title}"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = InkMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box {
                    IconButton(
                        onClick = { showTransitionMenu = true },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("mobile_transition_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Animation,
                            contentDescription = "Gaya Transisi",
                            tint = SkyBlue
                        )
                    }
                    DropdownMenu(
                        expanded = showTransitionMenu,
                        onDismissRequest = { showTransitionMenu = false }
                    ) {
                        TransitionStyle.entries.forEach { style ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = if (style == transitionStyle) "✓ ${style.label}" else style.label,
                                        fontWeight = if (style == transitionStyle) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    onSelectTransition(style)
                                    showTransitionMenu = false
                                }
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onToggleOverview,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("mobile_overview_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = "Daftar Halaman",
                        tint = InkNavy
                    )
                }

                IconButton(
                    onClick = onToggleFullscreen,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("mobile_fullscreen_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Mode Slide Penuh",
                        tint = InkNavy
                    )
                }
            }
        }
    }
}

@Composable
fun MobileBottomNavBar(
    currentSlide: SlideDestination,
    onSelectSlide: (SlideDestination) -> Unit
) {
    val items = listOf(
        Triple("Sampul", Icons.Default.Home, SlideDestination.COVER),
        Triple("Menu", Icons.Default.Menu, SlideDestination.MENU),
        Triple("Materi", Icons.AutoMirrored.Filled.MenuBook, SlideDestination.MATERI_MENU),
        Triple("Games", Icons.Default.SportsEsports, SlideDestination.GAME_ARENA),
        Triple("Evaluasi", Icons.Default.Quiz, SlideDestination.EVALUASI_START)
    )

    NavigationBar(
        containerColor = CreamCard,
        tonalElevation = 6.dp,
        windowInsets = WindowInsets.navigationBars
    ) {
        items.forEach { (label, icon, dest) ->
            val selected = when (dest) {
                SlideDestination.MATERI_MENU -> currentSlide.section == "Materi"
                SlideDestination.EVALUASI_START -> currentSlide.section == "Evaluasi"
                else -> currentSlide == dest
            }
            NavigationBarItem(
                selected = selected,
                onClick = { onSelectSlide(dest) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = InkNavy,
                    selectedTextColor = InkNavy,
                    indicatorColor = SunGold.copy(alpha = 0.55f),
                    unselectedIconColor = InkMuted,
                    unselectedTextColor = InkMuted
                ),
                modifier = Modifier.testTag("mobile_nav_${label.lowercase()}")
            )
        }
    }
}

@Composable
fun MobileSlideCompanionPanel(
    slide: SlideDestination,
    uiState: PresentationUiState,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onNavigate: (SlideDestination) -> Unit,
    onAnswerQuiz: (Int, String) -> Unit,
    onResetQuiz: () -> Unit,
    onSelectRating: (AppRating) -> Unit,
    onToggleQrDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Quick Prev / Next swipe control row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onPrevious,
                enabled = slide.pageNumber > 1,
                shape = RoundedCornerShape(14.dp),
                color = if (slide.pageNumber > 1) TealButton else CreamWarm,
                modifier = Modifier
                    .height(48.dp)
                    .minimumInteractiveComponentSize()
                    .testTag("mobile_prev_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(horizontal = 14.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Sebelumnya",
                        tint = if (slide.pageNumber > 1) Color.White else InkMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Kembali",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (slide.pageNumber > 1) Color.White else InkMuted
                    )
                }
            }

            Text(
                text = "Geser atau ketuk tombol",
                style = MaterialTheme.typography.labelSmall,
                color = InkMuted
            )

            Surface(
                onClick = onNext,
                enabled = slide.pageNumber < 26,
                shape = RoundedCornerShape(14.dp),
                color = if (slide.pageNumber < 26) SunAmber else CreamWarm,
                modifier = Modifier
                    .height(48.dp)
                    .minimumInteractiveComponentSize()
                    .testTag("mobile_next_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(horizontal = 14.dp)
                ) {
                    Text(
                        text = "Lanjut",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (slide.pageNumber < 26) Color.White else InkMuted
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Berikutnya",
                        tint = if (slide.pageNumber < 26) Color.White else InkMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Context-specific large mobile controls matching the current slide
        when (slide) {
            SlideDestination.COVER -> {
                Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, null, tint = InkNavy)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Mulai Petualangan Belajar", color = InkNavy, fontWeight = FontWeight.Bold)
                }
                MobileInfoCard(
                    title = "Dunia Usaha • PGSD UNM",
                    body = "Media pembelajaran interaktif bahasa Indonesia & kewirausahaan SD kelas IV. Geser ke kanan atau tekan tombol Mulai."
                )
            }

            SlideDestination.HALLO -> MobileInfoCard(
                title = "Halo Pengusaha Cilik!",
                body = "Hari ini kita akan mengenal dunia usaha, menemukan ide kreatif, cara berjualan, dan sikap menjadi pengusaha yang sukses."
            )

            SlideDestination.KENALAN -> MobileInfoCard(
                title = "Apa Itu Wirausaha?",
                body = "Kegiatan menjalankan usaha dengan memanfaatkan ide, kreativitas, dan kemampuan untuk menghasilkan barang atau jasa agar kita menjadi anak kreatif, mandiri, berani mencoba, dan pantang menyerah."
            )

            SlideDestination.MENU -> {
                Text(
                    text = "Pilih Menu Cepat (Versi Mobile):",
                    style = MaterialTheme.typography.titleMedium,
                    color = InkNavy
                )
                val menuItems = listOf(
                    "Petunjuk" to SlideDestination.PETUNJUK,
                    "Profil" to SlideDestination.PROFIL_TIM,
                    "Tujuan" to SlideDestination.CAPAIAN,
                    "Materi" to SlideDestination.MATERI_MENU,
                    "Games" to SlideDestination.GAME_ARENA,
                    "Evaluasi" to SlideDestination.EVALUASI_START
                )
                menuItems.chunked(2).forEach { pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        pair.forEach { (label, dest) ->
                            Surface(
                                onClick = { onNavigate(dest) },
                                shape = RoundedCornerShape(16.dp),
                                color = SunGold.copy(alpha = 0.35f),
                                border = BorderStroke(1.5.dp, SunAmber),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .minimumInteractiveComponentSize()
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(label, style = MaterialTheme.typography.titleMedium, color = InkNavy)
                                }
                            }
                        }
                    }
                }
            }

            SlideDestination.MATERI_MENU -> {
                Text("Pilih Topik Materi:", style = MaterialTheme.typography.titleMedium, color = InkNavy)
                val topics = listOf(
                    "1. Mengenal Wirausaha" to SlideDestination.MATERI_WIRAUSAHA,
                    "2. Sikap Wirausahawan" to SlideDestination.MATERI_SIKAP,
                    "3. Kosakata dan Idiom" to SlideDestination.MATERI_KOSAKATA,
                    "4. Ide Pokok & Teks Informatif" to SlideDestination.MATERI_IDE_POKOK,
                    "5. Wawancara dan Laporan" to SlideDestination.MATERI_WAWANCARA
                )
                topics.forEach { (label, dest) ->
                    Surface(
                        onClick = { onNavigate(dest) },
                        shape = RoundedCornerShape(14.dp),
                        color = CreamCard,
                        border = BorderStroke(1.5.dp, SunAmber),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        ) {
                            Text(label, style = MaterialTheme.typography.titleMedium, color = InkNavy)
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = SunAmber, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }

            SlideDestination.MATERI_WIRAUSAHA -> MobileInfoCard(
                title = "Contoh Kegiatan Wirausaha",
                body = DuniaUsahaContent.entrepreneurExamples.joinToString("\n") { "• $it" }
            )

            SlideDestination.MATERI_SIKAP -> {
                MobileInfoCard(
                    title = "7 Sikap Wirausahawan",
                    body = DuniaUsahaContent.entrepreneurAttitudes
                        .mapIndexed { idx, (name, desc) -> "${idx + 1}. $name: $desc" }
                        .joinToString("\n")
                )
            }

            SlideDestination.MATERI_KOSAKATA -> {
                MobileInfoCard(
                    title = "Ringkasan Kosakata & Idiom",
                    body = DuniaUsahaContent.vocabList.joinToString("\n") { "• ${it.term}: ${it.meaning}" } +
                        "\n\nIdiom Penting:\n" +
                        DuniaUsahaContent.idiomList.joinToString("\n") { "• ${it.idiom}: ${it.meaning}" }
                )
            }

            SlideDestination.MATERI_IDE_POKOK -> MobileInfoCard(
                title = "Ide Pokok Usaha Kue Bu Siti",
                body = "Ide Pokok: Bu Siti menjalankan usaha kue tradisional.\n\nLangkah Menemukan:\n1. Baca paragraf teliti\n2. Temukan hal yang paling banyak dibahas\n3. Perhatikan kalimat utama\n4. Simpulkan inti paragraf"
            )

            SlideDestination.MATERI_WAWANCARA -> MobileInfoCard(
                title = "Langkah Wawancara Pedagang",
                body = DuniaUsahaContent.interviewSteps
                    .mapIndexed { idx, s -> "${idx + 1}. $s" }
                    .joinToString("\n")
            )

            SlideDestination.EVALUASI_START -> {
                Button(
                    onClick = { onNavigate(SlideDestination.SOAL_1) },
                    colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                ) {
                    Text("START EVALUASI", style = MaterialTheme.typography.titleLarge, color = Color.White)
                }
            }

            SlideDestination.SOAL_1,
            SlideDestination.SOAL_2,
            SlideDestination.SOAL_3,
            SlideDestination.SOAL_4 -> {
                val qNum = slide.pageNumber - 18
                val q = DuniaUsahaContent.quizQuestions[qNum - 1]
                val chosen = uiState.quizAnswers[qNum]

                Text(
                    text = "Pilih Jawaban Soal $qNum:",
                    style = MaterialTheme.typography.titleMedium,
                    color = InkNavy
                )
                q.options.forEach { opt ->
                    val isSelected = chosen == opt.letter
                    val isCorrect = opt.letter == q.correctLetter
                    val bg = when {
                        chosen == null -> CreamCard
                        isSelected && isCorrect -> MeadowMint
                        isSelected && !isCorrect -> RoseSoft
                        isCorrect -> MeadowMint.copy(alpha = 0.6f)
                        else -> CreamCard
                    }
                    Surface(
                        onClick = { onAnswerQuiz(qNum, opt.letter) },
                        shape = RoundedCornerShape(14.dp),
                        color = bg,
                        border = BorderStroke(if (isSelected) 2.5.dp else 1.5.dp, if (isSelected) InkNavy else SkyBlue.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Surface(shape = CircleShape, color = SkyBlue, modifier = Modifier.size(32.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(opt.letter, style = MaterialTheme.typography.labelLarge, color = Color.White)
                                }
                            }
                            Text(opt.text, style = MaterialTheme.typography.bodyMedium, color = InkNavy, modifier = Modifier.weight(1f))
                            if (chosen != null && isCorrect) {
                                Icon(Icons.Default.CheckCircle, null, tint = MeadowGreen, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
                AnimatedVisibility(visible = chosen != null) {
                    MobileInfoCard(
                        title = if (chosen == q.correctLetter) "Jawaban Benar! ✓" else "Penjelasan",
                        body = q.explanation
                    )
                }
            }

            SlideDestination.EVALUASI_HASIL -> {
                MobileInfoCard(
                    title = "Hasil Evaluasi: ${uiState.quizCorrectCount}/4 Benar (${uiState.quizScorePercent} Poin)",
                    body = "Selamat! Kamu sudah menyelesaikan tantangan evaluasi wirausaha."
                )
                Button(
                    onClick = onResetQuiz,
                    colors = ButtonDefaults.buttonColors(containerColor = TealButton),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(Icons.Default.Refresh, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Ulangi Kuis")
                }
            }

            SlideDestination.REFLEKSI -> {
                Text("Pilih Penilaianmu:", style = MaterialTheme.typography.titleMedium, color = InkNavy)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppRating.entries.forEach { rating ->
                        val chosen = uiState.selectedRating == rating
                        Surface(
                            onClick = { onSelectRating(rating) },
                            shape = RoundedCornerShape(14.dp),
                            color = if (chosen) SunGold else CreamCard,
                            border = BorderStroke(1.5.dp, if (chosen) InkNavy else SunAmber),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(rating.label, style = MaterialTheme.typography.labelLarge, color = InkNavy)
                            }
                        }
                    }
                }
            }

            SlideDestination.SCAN_QR -> {
                Button(
                    onClick = { onToggleQrDialog(true) },
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(Icons.Default.QrCode2, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Buka Petunjuk Misi QR")
                }
            }

            SlideDestination.DAFTAR_PUSTAKA -> {
                MobileInfoCard(
                    title = "Daftar Pustaka",
                    body = DuniaUsahaContent.bibliography.mapIndexed { i, r -> "${i + 1}. $r" }.joinToString("\n\n")
                )
                Button(
                    onClick = { onNavigate(SlideDestination.COVER) },
                    colors = ButtonDefaults.buttonColors(containerColor = SunAmber),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(Icons.Default.Home, null, tint = InkNavy)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Kembali ke Sampul", color = InkNavy)
                }
            }

            else -> {
                MobileInfoCard(
                    title = slide.title,
                    body = "Ketuk area tombol pada gambar slide di atas atau gunakan tombol Kembali / Lanjut untuk bernavigasi."
                )
            }
        }
    }
}

@Composable
private fun MobileInfoCard(title: String, body: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CreamCard,
        border = BorderStroke(1.5.dp, InkNavy.copy(alpha = 0.16f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = CoralPinkDeep)
            Text(text = body, style = MaterialTheme.typography.bodyMedium, color = InkNavy)
        }
    }
}

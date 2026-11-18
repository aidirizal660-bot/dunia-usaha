package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DuniaUsahaContent
import com.example.ui.components.CuteKidBookIllustration
import com.example.ui.components.LayeredDisplayTitle
import com.example.ui.components.ShopkeeperBoyIllustration
import com.example.ui.components.SlideTopNavRow
import com.example.ui.components.SoftMarketBackdrop
import com.example.ui.components.StaggeredEntrance
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CoralPinkDeep
import com.example.ui.theme.CreamCard
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.InkMuted
import com.example.ui.theme.InkNavy
import com.example.ui.theme.MeadowGreen
import com.example.ui.theme.MeadowMint
import com.example.ui.theme.MeadowOutline
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold

@Composable
fun Slide11ApaItuWirausaha(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.2f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Surface(
                        shape = RoundedCornerShape(40.dp),
                        color = MeadowMint,
                        border = BorderStroke(3.dp, MeadowOutline),
                        shadowElevation = 4.dp
                    ) {
                        LayeredDisplayTitle(
                            text = "Apa itu wirausaha?",
                            fillColor = SunGold,
                            shadowColor = SkyBlue,
                            fontSize = 32.sp,
                            modifier = Modifier.padding(horizontal = 28.dp, vertical = 6.dp)
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StaggeredEntrance(delayMillis = 90, modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(36.dp),
                        color = MeadowMint,
                        border = BorderStroke(4.dp, MeadowOutline),
                        shadowElevation = 6.dp
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(28.dp)
                        ) {
                            Text(
                                text = "Wirausaha adalah kegiatan menjalankan usaha dengan memanfaatkan kreativitas, ide, dan kemampuan untuk menghasilkan barang atau jasa yang dapat dijual.",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 18.sp,
                                    lineHeight = 27.sp
                                ),
                                color = InkNavy,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                StaggeredEntrance(delayMillis = 190, modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(36.dp),
                        color = MeadowMint,
                        border = BorderStroke(4.dp, MeadowOutline),
                        shadowElevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Contoh kegiatan wirausaha:",
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                                color = MeadowOutline
                            )
                            DuniaUsahaContent.entrepreneurExamples.forEach { example ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MeadowGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = example,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                                        color = InkNavy
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Slide12SikapWirausahawan(
    highlightedIndex: Int,
    onSelectAttitude: (Int) -> Unit,
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
                    LayeredDisplayTitle(
                        text = "Sikap Wirausahawan",
                        fillColor = SunGold,
                        shadowColor = SkyBlue,
                        fontSize = 34.sp
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StaggeredEntrance(
                    delayMillis = 80,
                    modifier = Modifier
                        .weight(0.75f)
                        .fillMaxHeight()
                ) {
                    ShopkeeperBoyIllustration(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 8.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.25f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Sikap yang harus dimiliki wirausahawan: (klik untuk sorot)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = MeadowOutline
                    )

                    DuniaUsahaContent.entrepreneurAttitudes.forEachIndexed { idx, (name, desc) ->
                        val isSelected = idx == highlightedIndex
                        StaggeredEntrance(delayMillis = 50 + idx * 35) {
                            Surface(
                                onClick = { onSelectAttitude(idx) },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) SunGold.copy(alpha = 0.28f) else CreamCard.copy(alpha = 0.8f),
                                border = BorderStroke(
                                    1.5.dp,
                                    if (isSelected) SunAmber else InkNavy.copy(alpha = 0.12f)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("attitude_item_${idx + 1}")
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold, color = InkNavy)) {
                                            append("${idx + 1}. $name: ")
                                        }
                                        append(desc)
                                    },
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                                    color = InkNavy,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    StaggeredEntrance(delayMillis = 260) {
                        Surface(
                            shape = RoundedCornerShape(22.dp),
                            color = MeadowMint,
                            border = BorderStroke(2.5.dp, MeadowOutline),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = "Contoh perilaku:",
                                    style = MaterialTheme.typography.titleMedium.copy(fontFamily = FredokaFontFamily),
                                    color = MeadowOutline
                                )
                                Text(
                                    text = "Jika seorang penjual memberikan uang kembalian sesuai dengan jumlah yang seharusnya, berarti ia telah menunjukkan sikap jujur.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = InkNavy,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Slide13KosakataIdiom(
    selectedVocabIndex: Int?,
    selectedIdiomIndex: Int?,
    onSelectVocab: (Int) -> Unit,
    onSelectIdiom: (Int) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.16f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Surface(
                        shape = RoundedCornerShape(32.dp),
                        color = MeadowMint,
                        border = BorderStroke(2.5.dp, MeadowOutline)
                    ) {
                        LayeredDisplayTitle(
                            text = "Kosakata dan Idiom",
                            fillColor = SunGold,
                            shadowColor = SkyBlue,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Left column: A. Kosakata
                StaggeredEntrance(delayMillis = 70, modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MeadowGreen
                        ) {
                            Text(
                                text = "A. Kosakata (klik baris untuk contoh)",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CreamCard,
                            border = BorderStroke(1.5.dp, MeadowOutline)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MeadowGreen)
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = "Kosakata",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White,
                                        modifier = Modifier.weight(0.36f)
                                    )
                                    Text(
                                        text = "Artinya",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White,
                                        modifier = Modifier.weight(0.64f)
                                    )
                                }
                                DuniaUsahaContent.vocabList.forEachIndexed { idx, item ->
                                    val expanded = selectedVocabIndex == idx
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(if (expanded) MeadowMint else Color.Transparent)
                                            .clickable { onSelectVocab(idx) }
                                            .padding(horizontal = 12.dp, vertical = 7.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.Top) {
                                            Text(
                                                text = item.term,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
                                                color = InkNavy,
                                                modifier = Modifier.weight(0.36f)
                                            )
                                            Text(
                                                text = item.meaning,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = InkNavy,
                                                modifier = Modifier.weight(0.64f)
                                            )
                                        }
                                        AnimatedVisibility(visible = expanded) {
                                            Text(
                                                text = "Contoh: ${item.example}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MeadowOutline,
                                                modifier = Modifier.padding(top = 4.dp)
                                            )
                                        }
                                    }
                                    if (idx < DuniaUsahaContent.vocabList.lastIndex) {
                                        HorizontalDivider(color = MeadowOutline.copy(alpha = 0.25f))
                                    }
                                }
                            }
                        }
                    }
                }

                // Right column: B. Idiom
                StaggeredEntrance(delayMillis = 150, modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = CoralPinkDeep
                        ) {
                            Text(
                                text = "B. Idiom",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }

                        Text(
                            text = "Idiom adalah gabungan kata yang memiliki makna khusus dan tidak selalu dapat diartikan dari kata-kata penyusunnya.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = InkNavy
                        )

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CreamCard,
                            border = BorderStroke(1.5.dp, CoralPink)
                        ) {
                            Column {
                                DuniaUsahaContent.idiomList.forEachIndexed { idx, item ->
                                    val expanded = selectedIdiomIndex == idx
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(if (expanded) RoseSoft.copy(alpha = 0.7f) else Color.Transparent)
                                            .clickable { onSelectIdiom(idx) }
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Row {
                                            Text(
                                                text = "${item.idiom}:",
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.ExtraBold),
                                                color = CoralPinkDeep,
                                                modifier = Modifier.weight(0.36f)
                                            )
                                            Text(
                                                text = item.meaning,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = InkNavy,
                                                modifier = Modifier.weight(0.64f)
                                            )
                                        }
                                        AnimatedVisibility(visible = expanded) {
                                            Text(
                                                text = "Contoh: ${item.example}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = InkNavy,
                                                modifier = Modifier.padding(top = 3.dp)
                                            )
                                        }
                                    }
                                    if (idx < DuniaUsahaContent.idiomList.lastIndex) {
                                        HorizontalDivider(color = CoralPink.copy(alpha = 0.35f))
                                    }
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFFFFBE6),
                            border = BorderStroke(2.dp, SunGold)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EditNote,
                                    contentDescription = null,
                                    tint = MeadowGreen,
                                    modifier = Modifier.size(28.dp)
                                )
                                Column {
                                    Text(
                                        text = "Contoh kalimat:",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = SunOrangeColor
                                    )
                                    Text(
                                        text = buildAnnotatedString {
                                            append("Pak Budi bekerja ")
                                            withStyle(SpanStyle(color = CoralPinkDeep, fontWeight = FontWeight.Bold)) {
                                                append("banting tulang")
                                            }
                                            append(" untuk mengembangkan usahanya.\n")
                                            withStyle(SpanStyle(color = SunOrangeColor, fontWeight = FontWeight.Bold)) {
                                                append("Artinya: ")
                                            }
                                            append("Pak Budi bekerja keras untuk mengembangkan usahanya.")
                                        },
                                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                        color = InkNavy
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private val SunOrangeColor = Color(0xFFE67E22)

@Composable
fun Slide14IdePokok(
    highlightMainIdea: Boolean,
    onToggleHighlight: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.15f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    LayeredDisplayTitle(
                        text = "Ide pokok dan teks informatif",
                        fillColor = SunGold,
                        shadowColor = SkyBlue,
                        fontSize = 28.sp
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Column 1: Definitions
                StaggeredEntrance(delayMillis = 60, modifier = Modifier.weight(0.95f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        InfoSectionCard(
                            icon = Icons.Default.Lightbulb,
                            iconBg = MeadowGreen,
                            title = "Apa itu ide pokok?",
                            titleColor = MeadowOutline,
                            points = listOf(
                                "Ide pokok adalah gagasan utama yang menjadi dasar pembahasan dalam sebuah paragraf.",
                                "Ide pokok dapat ditemukan dengan membaca dan memahami isi paragraf."
                            )
                        )
                        InfoSectionCard(
                            icon = Icons.Default.Book,
                            iconBg = SkyBlue,
                            title = "Apa itu teks informatif?",
                            titleColor = SkyBlue,
                            points = listOf(
                                "Teks informatif adalah teks yang berisi informasi atau pengetahuan tentang suatu hal dan bertujuan memberikan informasi kepada pembaca."
                            )
                        )
                    }
                }

                // Column 2: Example Bu Siti + interactive highlight
                StaggeredEntrance(delayMillis = 130, modifier = Modifier.weight(1.25f)) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = CreamCard,
                        border = BorderStroke(2.dp, CoralPink),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = CoralPinkDeep
                                ) {
                                    Text(
                                        text = "Contoh teks informatif:",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                    )
                                }
                                Surface(
                                    onClick = onToggleHighlight,
                                    shape = RoundedCornerShape(20.dp),
                                    color = if (highlightMainIdea) SunGold else SunAmber.copy(alpha = 0.2f),
                                    border = BorderStroke(1.dp, SunAmber),
                                    modifier = Modifier.testTag("toggle_main_idea_highlight")
                                ) {
                                    Text(
                                        text = if (highlightMainIdea) "Sorotan Aktif ✓" else "Sorot Kalimat Utama",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = InkNavy,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }

                            Text(
                                text = "Usaha Kue Bu Siti",
                                style = MaterialTheme.typography.titleMedium,
                                color = CoralPinkDeep
                            )
                            Text(
                                text = buildAnnotatedString {
                                    if (highlightMainIdea) {
                                        withStyle(
                                            SpanStyle(
                                                background = SunGold.copy(alpha = 0.5f),
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        ) {
                                            append("Bu Siti adalah seorang penjual kue tradisional.")
                                        }
                                    } else {
                                        append("Bu Siti adalah seorang penjual kue tradisional.")
                                    }
                                    append(" Setiap pagi, ia membuat berbagai macam kue untuk dijual di pasar. Kue yang dijual antara lain klepon, onde-onde, dan kue lapis. Bu Siti selalu menjaga kebersihan dan kualitas kuenya agar pembeli merasa puas. Berkat kerja keras dan kejujurannya, usaha Bu Siti semakin dikenal masyarakat.")
                                },
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp, lineHeight = 19.sp),
                                color = InkNavy
                            )

                            HorizontalDivider(color = CoralPink.copy(alpha = 0.4f))

                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = RoseSoft.copy(alpha = 0.7f)
                            ) {
                                Text(
                                    text = "Menentukan ide pokok:",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = CoralPinkDeep,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(shape = CircleShape, color = CoralPinkDeep, modifier = Modifier.size(24.dp)) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Star, null, tint = SunGold, modifier = Modifier.size(15.dp))
                                    }
                                }
                                Text(
                                    text = "Ide pokok: Bu Siti menjalankan usaha kue tradisional.",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = InkNavy
                                )
                            }

                            Text(
                                text = "Informasi pendukung:",
                                style = MaterialTheme.typography.labelLarge,
                                color = CoralPinkDeep
                            )
                            Text(
                                text = "1. Bu Siti membuat kue setiap pagi.\n2. Kue dijual di pasar.\n3. Bu Siti menjaga kebersihan dan kualitas kue.\n4. Usahanya semakin dikenal masyarakat.",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 18.sp),
                                color = InkNavy
                            )
                        }
                    }
                }

                // Column 3: Steps
                StaggeredEntrance(delayMillis = 200, modifier = Modifier.weight(0.95f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = SunAmber
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.Search, null, tint = Color.White, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "Cara menemukan ide pokok:",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White
                                )
                            }
                        }

                        val steps = listOf(
                            "Membaca paragraf dengan teliti.",
                            "Menemukan hal yang paling banyak dibahas.",
                            "Memperhatikan kalimat utama.",
                            "Menyimpulkan inti paragraf."
                        )
                        steps.forEachIndexed { idx, step ->
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = CreamCard,
                                border = BorderStroke(1.5.dp, SunAmber.copy(alpha = 0.5f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Surface(shape = CircleShape, color = SunAmber, modifier = Modifier.size(28.dp)) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text("${idx + 1}.", style = MaterialTheme.typography.labelLarge, color = Color.White)
                                        }
                                    }
                                    Text(text = step, style = MaterialTheme.typography.bodyMedium, color = InkNavy)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoSectionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    title: String,
    titleColor: Color,
    points: List<String>
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CreamCard,
        border = BorderStroke(1.5.dp, iconBg.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(shape = CircleShape, color = iconBg, modifier = Modifier.size(30.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = titleColor)
            }
            points.forEach { pt ->
                Text(
                    text = "• $pt",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.5.sp, lineHeight = 19.sp),
                    color = InkNavy
                )
            }
        }
    }
}

@Composable
fun Slide15WawancaraLaporan(
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
                    LayeredDisplayTitle(
                        text = "wawancara dan laporan",
                        fillColor = SunGold,
                        shadowColor = SkyBlue,
                        fontSize = 30.sp
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 18.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Col 1: Apa itu wawancara?
                StaggeredEntrance(delayMillis = 60, modifier = Modifier.weight(0.95f)) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = CreamCard,
                        border = BorderStroke(2.dp, MeadowGreen),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(shape = CircleShape, color = MeadowGreen, modifier = Modifier.size(30.dp)) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Lightbulb, null, tint = SunGold, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Text(
                                    text = "Apa itu wawancara?",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MeadowOutline
                                )
                            }
                            Text(
                                text = "Wawancara adalah kegiatan tanya jawab antara pewawancara dan narasumber untuk memperoleh informasi mengenai suatu topik.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = InkNavy
                            )
                            HorizontalDivider(color = MeadowGreen.copy(alpha = 0.3f))
                            RoleRow("Pewawancara", "Orang yang mengajukan pertanyaan.")
                            RoleRow("Narasumber", "Orang yang memberikan informasi.")
                        }
                    }
                }

                // Col 2: Langkah & Pertanyaan
                StaggeredEntrance(delayMillis = 120, modifier = Modifier.weight(1.05f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = CreamCard,
                            border = BorderStroke(2.dp, CoralPink)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Surface(shape = RoundedCornerShape(12.dp), color = CoralPinkDeep) {
                                    Text(
                                        text = "Langkah-langkah melakukan wawancara:",
                                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                                DuniaUsahaContent.interviewSteps.forEachIndexed { idx, step ->
                                    NumberedStepLine(idx + 1, step, CoralPinkDeep)
                                }
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = CreamCard,
                            border = BorderStroke(2.dp, CoralPink)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Surface(shape = RoundedCornerShape(12.dp), color = CoralPinkDeep) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.HelpOutline, null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        Text(
                                            text = "Contoh pertanyaan wawancara kepada pedagang:",
                                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.5.sp),
                                            color = Color.White
                                        )
                                    }
                                }
                                DuniaUsahaContent.interviewQuestions.forEachIndexed { idx, q ->
                                    NumberedStepLine(idx + 1, q, CoralPinkDeep)
                                }
                            }
                        }
                    }
                }

                // Col 3: Contoh laporan hasil wawancara
                StaggeredEntrance(delayMillis = 180, modifier = Modifier.weight(1.05f)) {
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = CreamCard,
                        border = BorderStroke(2.dp, SkyBlue),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(shape = RoundedCornerShape(12.dp), color = Color(0xFF1A62B5)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                                ) {
                                    Icon(Icons.Default.Assignment, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    Text(
                                        text = "Contoh laporan hasil wawancara:",
                                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.5.sp),
                                        color = Color.White
                                    )
                                }
                            }
                            ReportLine("Judul:", "Hasil Wawancara dengan Penjual Kue")
                            ReportLine("Narasumber:", "Ibu Siti")
                            ReportLine("Topik:", "Kegiatan Usaha Kue")
                            HorizontalDivider(color = SkyBlue.copy(alpha = 0.35f))
                            Text(
                                text = "Hasil wawancara:",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFF1A62B5)
                            )
                            Text(
                                text = "Berdasarkan hasil wawancara dengan Ibu Siti, diketahui bahwa beliau telah menjalankan usaha kue selama tiga tahun. Beliau memiliki usaha tersebut karena memiliki keterampilan membuat kue. Produk yang dijual antara lain kue lapis, klepon, dan onde-onde. Menurut Ibu Siti, kejujuran, kebersihan, dan keramahan merupakan hal penting dalam menjalankan usaha.",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp, lineHeight = 18.sp),
                                color = InkNavy
                            )
                            Text(
                                text = "Kesimpulan:",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color(0xFF1A62B5)
                            )
                            Text(
                                text = "Berwirausaha membutuhkan kerja keras, kreativitas, kejujuran, dan tanggung jawab agar usaha dapat berkembang.",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp, lineHeight = 18.sp),
                                color = InkNavy
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RoleRow(role: String, desc: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(shape = CircleShape, color = MeadowGreen, modifier = Modifier.size(20.dp)) {}
        Text(
            text = "$role: $desc",
            style = MaterialTheme.typography.bodyMedium,
            color = InkNavy
        )
    }
}

@Composable
private fun NumberedStepLine(num: Int, text: String, badgeColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Surface(shape = CircleShape, color = badgeColor, modifier = Modifier.size(19.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Text("$num", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color.White)
            }
        }
        Text(text = text, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp), color = InkNavy)
    }
}

@Composable
private fun ReportLine(label: String, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = Color(0xFF1A62B5), fontWeight = FontWeight.Bold)) {
                append("$label ")
            }
            append(value)
        },
        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
        color = InkNavy
    )
}

@Composable
fun Slide16TahukahKamu(
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
                onNext = onNext
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                StaggeredEntrance(delayMillis = 70, modifier = Modifier.weight(0.65f)) {
                    CuteKidBookIllustration(
                        isGirl = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
                StaggeredEntrance(delayMillis = 150, modifier = Modifier.weight(1.35f)) {
                    Surface(
                        shape = RoundedCornerShape(48.dp),
                        color = CreamCard,
                        border = BorderStroke(4.dp, InkNavy),
                        shadowElevation = 8.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 34.dp, vertical = 32.dp)
                        ) {
                            Text(
                                text = "Tahukah kamu?",
                                style = MaterialTheme.typography.headlineLarge,
                                color = CoralPinkDeep
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Anak-anak juga bisa belajar berwirausaha melalui kegiatan sederhana, seperti membuat kerajinan dan menjual hasil karya bersama teman.",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                                    fontSize = 21.sp,
                                    lineHeight = 31.sp
                                ),
                                color = InkNavy,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Slide17Kesimpulan(
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
                onNext = onNext
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 32.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                StaggeredEntrance(delayMillis = 70, modifier = Modifier.weight(0.6f)) {
                    CuteKidBookIllustration(
                        isGirl = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )
                }
                StaggeredEntrance(delayMillis = 140, modifier = Modifier.weight(1.4f)) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        Surface(
                            shape = RoundedCornerShape(30.dp),
                            color = CreamCard,
                            border = BorderStroke(3.5.dp, InkNavy),
                            shadowElevation = 6.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            Text(
                                text = "Wirausaha merupakan kegiatan yang bermanfaat bagi diri sendiri dan masyarakat. Untuk menjadi wirausahawan yang sukses, kita harus memiliki sikap jujur, disiplin, kreatif, mandiri, dan bertanggung jawab. Melalui kegiatan membaca, memahami kosakata, menentukan ide pokok, serta melakukan wawancara, kita dapat belajar mengenal dunia wirausaha dengan lebih baik.",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 17.sp,
                                    lineHeight = 26.sp
                                ),
                                color = InkNavy,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(
                                    start = 28.dp,
                                    end = 28.dp,
                                    top = 34.dp,
                                    bottom = 24.dp
                                )
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = RoseSoft,
                            border = BorderStroke(2.5.dp, InkNavy),
                            shadowElevation = 4.dp
                        ) {
                            Text(
                                text = "Kesimpulan",
                                style = MaterialTheme.typography.headlineMedium,
                                color = InkNavy,
                                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

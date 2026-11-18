package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiObjects
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DeveloperProfile
import com.example.model.DuniaUsahaContent
import com.example.model.SlideDestination
import com.example.ui.components.LayeredDisplayTitle
import com.example.ui.components.PersonAvatarBadge
import com.example.ui.components.SlideTopNavRow
import com.example.ui.components.SoftMarketBackdrop
import com.example.ui.components.StaggeredEntrance
import com.example.ui.theme.CoralPink
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.InkNavy
import com.example.ui.theme.LavenderCloud
import com.example.ui.theme.LavenderShadow
import com.example.ui.theme.RoseBorder
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import com.example.ui.theme.SunOrange

@Composable
fun Slide6ProfilTim(
    onMenu: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.2f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = true,
                showBack = true,
                showNext = true,
                onMenu = onMenu,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LayeredDisplayTitle(
                            text = "Profil pengembangan",
                            fillColor = Color(0xFF7B9EFF),
                            shadowColor = SkyBlue,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "Pendidikan Guru Sekolah Dasar • Universitas Negeri Makassar",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF1E5AA8)
                        )
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    StaggeredEntrance(delayMillis = 80, modifier = Modifier.weight(1f)) {
                        DeveloperCloudCard(profile = DuniaUsahaContent.developers[0])
                    }
                    StaggeredEntrance(delayMillis = 160, modifier = Modifier.weight(1f)) {
                        DeveloperCloudCard(profile = DuniaUsahaContent.developers[2])
                    }
                }
                StaggeredEntrance(
                    delayMillis = 240,
                    modifier = Modifier.fillMaxWidth(0.54f)
                ) {
                    DeveloperCloudCard(profile = DuniaUsahaContent.developers[1])
                }
            }
        }
    }
}

@Composable
private fun DeveloperCloudCard(profile: DeveloperProfile) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            shape = RoundedCornerShape(36.dp),
            color = LavenderShadow,
            modifier = Modifier
                .fillMaxWidth()
                .height(106.dp)
                .offset(x = 5.dp, y = 5.dp)
        ) {}
        Surface(
            shape = RoundedCornerShape(36.dp),
            color = LavenderCloud,
            border = BorderStroke(2.dp, LavenderShadow.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .height(106.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PersonAvatarBadge(
                    hijabColor = Color(profile.hijabColorHex),
                    blazerColor = Color(profile.blazerColorHex)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Nama : ${profile.name}",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                        color = InkNavy
                    )
                    Text(
                        text = "Kelas : ${profile.kelas}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkNavy
                    )
                    Text(
                        text = "NIM : ${profile.nim}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InkNavy
                    )
                }
            }
        }
    }
}

@Composable
fun Slide7ProfilDosen(
    onMenu: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.2f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = true,
                showBack = true,
                showNext = true,
                onMenu = onMenu,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LayeredDisplayTitle(
                            text = "Profil pengembangan",
                            fillColor = Color(0xFF7B9EFF),
                            shadowColor = SkyBlue,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "Pendidikan Guru Sekolah Dasar • Universitas Negeri Makassar",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF1E5AA8)
                        )
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 42.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                StaggeredEntrance(delayMillis = 120) {
                    Box(modifier = Modifier.fillMaxWidth(0.76f)) {
                        Surface(
                            shape = RoundedCornerShape(48.dp),
                            color = RoseBorder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .offset(x = 6.dp, y = 7.dp)
                        ) {}
                        Surface(
                            shape = RoundedCornerShape(48.dp),
                            color = Color(0xFFFFD1DC),
                            border = BorderStroke(2.dp, RoseBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 32.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "IBU DOSEN KHOTIMAH, S.Pd., M.Pd",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontFamily = FredokaFontFamily,
                                            fontSize = 23.sp
                                        ),
                                        color = Color(0xFF5D213B)
                                    )
                                    Text(
                                        text = "DOSEN PENGAMPU MATA KULIAH\nMULTIMEDIA PEMBELAJARAN",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color(0xFF5D213B).copy(alpha = 0.9f)
                                    )
                                }
                                PersonAvatarBadge(
                                    hijabColor = Color(0xFFD7B8A5),
                                    blazerColor = Color(0xFFFFFFFF),
                                    hasGlasses = true,
                                    modifier = Modifier.size(116.dp)
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
fun Slide8CapaianPembelajaran(
    onMenu: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.22f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = true,
                showBack = true,
                showNext = true,
                onMenu = onMenu,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    LayeredDisplayTitle(
                        text = "Capaian pembelajaran",
                        fillColor = SunGold,
                        shadowColor = SkyBlue,
                        fontSize = 34.sp
                    )
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 56.dp, vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                StaggeredEntrance(delayMillis = 110) {
                    Box(modifier = Modifier.fillMaxWidth(0.84f)) {
                        Surface(
                            shape = RoundedCornerShape(56.dp),
                            color = Color(0xFFD16A28),
                            modifier = Modifier
                                .matchParentSize()
                                .offset(x = 4.dp, y = 8.dp)
                        ) {}
                        Surface(
                            shape = RoundedCornerShape(56.dp),
                            color = Color(0xFFF6A54C),
                            border = BorderStroke(2.5.dp, Color(0xFFD16A28)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Peserta didik mampu memahami informasi dari teks lisan, tulisan, dan visual tentang kegiatan wirausaha. Peserta didik juga mampu membaca dan memahami teks informatif, menemukan ide pokok, memahami kosakata dan ungkapan, serta menyampaikan informasi dan pendapat secara lisan maupun tulisan dengan bahasa yang jelas dan santun.",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 19.sp,
                                    lineHeight = 30.sp
                                ),
                                color = InkNavy,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 42.dp, vertical = 34.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Slide9TujuanPembelajaran(
    onMenu: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.2f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = true,
                showBack = true,
                showNext = true,
                onMenu = onMenu,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    LayeredDisplayTitle(
                        text = "Tujuan Pembelajaran",
                        fillColor = CoralPink,
                        shadowColor = SkyBlue,
                        fontSize = 34.sp
                    )
                }
            )

            val objectives = DuniaUsahaContent.learningObjectives
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 36.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    objectives.take(3).forEachIndexed { idx, text ->
                        StaggeredEntrance(delayMillis = 70 + idx * 70) {
                            ObjectivePillCard(text = text)
                        }
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    objectives.drop(3).forEachIndexed { idx, text ->
                        StaggeredEntrance(delayMillis = 100 + idx * 70) {
                            ObjectivePillCard(text = text)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ObjectivePillCard(text: String) {
    Surface(
        shape = RoundedCornerShape(40.dp),
        color = RoseSoft,
        border = BorderStroke(3.dp, RoseBorder),
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                ),
                color = RoseBorder,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Slide10MateriMenu(
    onMenu: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onSelectTopic: (SlideDestination) -> Unit
) {
    val topics = listOf(
        Triple("Mengenal Wirausaha", Icons.Default.Storefront, SlideDestination.MATERI_WIRAUSAHA),
        Triple("Sikap Wirausahawan", Icons.Default.EmojiObjects, SlideDestination.MATERI_SIKAP),
        Triple("Kosakata dan Idiom", Icons.Default.Chat, SlideDestination.MATERI_KOSAKATA),
        Triple("Ide Pokok dan Teks Informatif", Icons.Default.MenuBook, SlideDestination.MATERI_IDE_POKOK),
        Triple("Wawancara dan Laporan", Icons.Default.RecordVoiceOver, SlideDestination.MATERI_WAWANCARA)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.25f)
        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = true,
                showBack = true,
                showNext = true,
                onMenu = onMenu,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    LayeredDisplayTitle(
                        text = "Materi",
                        fillColor = SunGold,
                        shadowColor = SkyBlue,
                        fontSize = 40.sp
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
            ) {
                topics.forEachIndexed { index, (title, icon, dest) ->
                    StaggeredEntrance(
                        delayMillis = 60 + index * 60,
                        modifier = Modifier.fillMaxWidth(0.56f)
                    ) {
                        MateriTopicBar(
                            title = title,
                            icon = icon,
                            testTag = "materi_topic_${index + 1}",
                            onClick = { onSelectTopic(dest) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MateriTopicBar(
    title: String,
    icon: ImageVector,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = SunGold,
        border = BorderStroke(2.5.dp, InkNavy),
        shadowElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = SunGold,
                border = BorderStroke(4.dp, SkyBlue),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = InkNavy,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                color = InkNavy,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.AutoStories,
                contentDescription = null,
                tint = InkNavy.copy(alpha = 0.55f),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(18.dp)
            )
        }
    }
}

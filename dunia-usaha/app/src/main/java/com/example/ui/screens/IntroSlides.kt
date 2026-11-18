package com.example.ui.screens

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
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SlideDestination
import com.example.ui.components.CandyStallIllustration
import com.example.ui.components.CoverMarketStallIllustration
import com.example.ui.components.LayeredDisplayTitle
import com.example.ui.components.PicketFenceStrip
import com.example.ui.components.SlideTopNavRow
import com.example.ui.components.SoftMarketBackdrop
import com.example.ui.components.SpinningSunDecoration
import com.example.ui.components.StaggeredEntrance
import com.example.ui.components.YellowCircleNavButton
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CoralPinkDeep
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCard
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.InkNavy
import com.example.ui.theme.RoseSoft
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import com.example.ui.theme.SunOrange
import com.example.ui.theme.WoodBrown

@Composable
fun Slide1Cover(onStart: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "coverPulse")
    val playPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "playButtonPulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBackground)
    ) {
        // Bottom wooden picket fence (left half)
        PicketFenceStrip(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.58f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 14.dp)
        ) {
            // Top institutional badges: Tut Wuri Handayani, UNM, PGSD
            StaggeredEntrance(delayMillis = 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    InstitutionBadge("Tut Wuri", SkyBlue)
                    InstitutionBadge("UNM", InkNavy)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SunOrange.copy(alpha = 0.16f),
                        border = BorderStroke(1.5.dp, SunOrange)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Program Studi PGSD",
                                tint = SunOrange,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Program Studi PGSD",
                                style = MaterialTheme.typography.labelLarge,
                                color = InkNavy
                            )
                        }
                    }
                }
            }

            // Main two-column hero area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Title & Play button
                Column(
                    modifier = Modifier
                        .weight(1.05f)
                        .padding(bottom = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    StaggeredEntrance(delayMillis = 90) {
                        LayeredDisplayTitle(
                            text = "DUNIA\nUSAHA",
                            fillColor = CoralPink,
                            shadowColor = SunGold,
                            fontSize = 56.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    StaggeredEntrance(delayMillis = 190) {
                        Surface(
                            onClick = onStart,
                            shape = CircleShape,
                            color = SunAmber,
                            shadowElevation = 10.dp,
                            border = BorderStroke(4.dp, WoodBrown),
                            modifier = Modifier
                                .size(96.dp)
                                .minimumInteractiveComponentSize()
                                .graphicsLayer {
                                    scaleX = playPulse
                                    scaleY = playPulse
                                }
                                .testTag("cover_play_button")
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(SunGold, SunAmber, SunOrange)
                                        )
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Mulai Belajar Dunia Usaha",
                                    tint = Color(0xFF7B241C),
                                    modifier = Modifier.size(54.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    StaggeredEntrance(delayMillis = 260) {
                        Text(
                            text = "Klik untuk Memulai Petualangan!",
                            style = MaterialTheme.typography.labelLarge,
                            color = InkNavy.copy(alpha = 0.78f)
                        )
                    }
                }

                // Right: Illustrated Market Stall
                StaggeredEntrance(
                    delayMillis = 150,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    CoverMarketStallIllustration(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 6.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun InstitutionBadge(label: String, tint: Color) {
    Surface(
        shape = CircleShape,
        color = tint.copy(alpha = 0.14f),
        border = BorderStroke(1.5.dp, tint)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = tint,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun Slide2Hallo(
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
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                StaggeredEntrance(
                    delayMillis = 80,
                    modifier = Modifier
                        .weight(0.95f)
                        .fillMaxHeight()
                ) {
                    CandyStallIllustration(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 8.dp)
                    )
                }

                StaggeredEntrance(
                    delayMillis = 160,
                    modifier = Modifier.weight(1.15f)
                ) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        // Speech bubble card
                        Surface(
                            shape = RoundedCornerShape(36.dp),
                            color = CreamCard,
                            border = BorderStroke(3.5.dp, InkNavy),
                            shadowElevation = 6.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            Text(
                                text = "Yuk, hari ini kita belajar sambil bermain tentang Pengusaha cilik! Kita akan mengenal dunia usaha, belajar menemukan ide kreatif, mengetahui cara berjualan, dan memahami bagaimana menjadi seorang pengusaha yang sukses.\nSudah siap untuk memulai petualangan kita hari ini?",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    lineHeight = 25.sp
                                ),
                                color = InkNavy,
                                modifier = Modifier.padding(
                                    start = 26.dp,
                                    end = 26.dp,
                                    top = 34.dp,
                                    bottom = 24.dp
                                )
                            )
                        }

                        // Floating "hallo" badge
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = SunGold,
                            border = BorderStroke(2.5.dp, InkNavy),
                            shadowElevation = 4.dp
                        ) {
                            Text(
                                text = "hallo",
                                style = MaterialTheme.typography.displayMedium.copy(fontSize = 32.sp),
                                color = CoralPinkDeep,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Slide3Kenalan(
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.16f)
        PicketFenceStrip(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        SpinningSunDecoration(modifier = Modifier.size(60.dp))
                        LayeredDisplayTitle(
                            text = "Ayo kita kenalan",
                            fillColor = CoralPink,
                            shadowColor = SunGold,
                            fontSize = 36.sp
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 28.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StaggeredEntrance(delayMillis = 90, modifier = Modifier.weight(1f)) {
                    PinkCloudCard(
                        title = "Apa itu wirausaha?",
                        body = "Wirausaha adalah kegiatan menjalankan usaha dengan memanfaatkan ide, kreativitas, dan kemampuan untuk menghasilkan barang atau jasa."
                    )
                }
                StaggeredEntrance(delayMillis = 190, modifier = Modifier.weight(1f)) {
                    PinkCloudCard(
                        title = "Mengapa kita perlu belajar wirausaha?",
                        body = "Dengan belajar wirausaha, kita dapat menjadi anak yang kreatif, mandiri, berani mencoba hal baru, dan pantang menyerah."
                    )
                }
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
private fun PinkCloudCard(title: String, body: String) {
    Surface(
        shape = RoundedCornerShape(38.dp),
        color = RoseSoft,
        border = BorderStroke(3.5.dp, InkNavy),
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 22.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp
                ),
                color = InkNavy,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp, lineHeight = 22.sp),
                color = InkNavy,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Slide4Menu(
    onBack: () -> Unit,
    onNext: () -> Unit,
    onHome: () -> Unit,
    onNavigate: (SlideDestination) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SoftMarketBackdrop(alpha = 0.25f)

        Column(modifier = Modifier.fillMaxSize()) {
            SlideTopNavRow(
                showMenu = false,
                showBack = true,
                showNext = true,
                onBack = onBack,
                onNext = onNext,
                centerContent = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CreamCard.copy(alpha = 0.85f),
                            border = BorderStroke(1.5.dp, WoodBrown.copy(alpha = 0.45f))
                        ) {
                            Text(
                                text = "FLEA MARKET TODAY!",
                                style = MaterialTheme.typography.labelSmall,
                                color = WoodBrown,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 3.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        LayeredDisplayTitle(
                            text = "Menu",
                            fillColor = Color(0xFFB3C7FF),
                            shadowColor = SkyBlue,
                            fontSize = 40.sp
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 52.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StaggeredEntrance(delayMillis = 70) {
                        MenuPillButton(
                            label = "petunjuk",
                            orbOnRight = true,
                            testTag = "menu_petunjuk_button",
                            onClick = { onNavigate(SlideDestination.PETUNJUK) }
                        )
                    }
                    StaggeredEntrance(delayMillis = 130) {
                        MenuPillButton(
                            label = "profil",
                            orbOnRight = true,
                            testTag = "menu_profil_button",
                            onClick = { onNavigate(SlideDestination.PROFIL_TIM) }
                        )
                    }
                    StaggeredEntrance(delayMillis = 190) {
                        MenuPillButton(
                            label = "tujuan",
                            orbOnRight = true,
                            testTag = "menu_tujuan_button",
                            onClick = { onNavigate(SlideDestination.CAPAIAN) }
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StaggeredEntrance(delayMillis = 100) {
                        MenuPillButton(
                            label = "materi",
                            orbOnRight = false,
                            testTag = "menu_materi_button",
                            onClick = { onNavigate(SlideDestination.MATERI_MENU) }
                        )
                    }
                    StaggeredEntrance(delayMillis = 160) {
                        MenuPillButton(
                            label = "games",
                            orbOnRight = false,
                            testTag = "menu_games_button",
                            onClick = { onNavigate(SlideDestination.GAME_ARENA) }
                        )
                    }
                    StaggeredEntrance(delayMillis = 220) {
                        MenuPillButton(
                            label = "evaluasi",
                            orbOnRight = false,
                            testTag = "menu_evaluasi_button",
                            onClick = { onNavigate(SlideDestination.EVALUASI_START) }
                        )
                    }
                }
            }
        }

        // Bottom-right pink Home button (Sampul)
        YellowCircleNavButton(
            icon = Icons.Default.Home,
            contentDescription = "Kembali ke Sampul",
            testTag = "menu_home_button",
            containerColor = CoralPink,
            onClick = onHome,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 22.dp, bottom = 14.dp)
        )
    }
}

@Composable
private fun MenuPillButton(
    label: String,
    orbOnRight: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "menuPillScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        // Hard cartoon offset shadow
        Surface(
            shape = RoundedCornerShape(50),
            color = InkNavy,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .offset(x = 4.dp, y = 4.dp)
        ) {}

        Surface(
            onClick = onClick,
            interactionSource = interactionSource,
            shape = RoundedCornerShape(50),
            color = SunAmber,
            border = BorderStroke(2.5.dp, InkNavy),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .minimumInteractiveComponentSize()
                .testTag(testTag)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (!orbOnRight) {
                    PurpleOrbIndicator()
                } else {
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FredokaFontFamily,
                        fontSize = 26.sp
                    ),
                    color = Color(0xFF4A1942)
                )

                if (orbOnRight) {
                    PurpleOrbIndicator()
                } else {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable
private fun PurpleOrbIndicator() {
    Box(
        modifier = Modifier
            .size(44.dp)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFFE0DCFF), Color(0xFF7B68EE), Color(0xFF3E1F47))
                ),
                shape = CircleShape
            )
    )
}

@Composable
fun Slide5Petunjuk(
    onMenu: () -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onHome: () -> Unit,
    onInfo: () -> Unit
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
                        text = "petunjuk penggunaan",
                        fillColor = SunGold,
                        shadowColor = InkNavy,
                        fontSize = 32.sp
                    )
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 42.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StaggeredEntrance(delayMillis = 70) {
                        PetunjukRow(
                            label = "Mulai",
                            subtitle = "Memulai petualangan belajar",
                            icon = Icons.Default.PlayArrow,
                            iconOnRight = true,
                            onClick = onNext
                        )
                    }
                    StaggeredEntrance(delayMillis = 130) {
                        PetunjukRow(
                            label = "Kembali",
                            subtitle = "Ke halaman sebelumnya",
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            iconOnRight = true,
                            onClick = onBack
                        )
                    }
                    StaggeredEntrance(delayMillis = 190) {
                        PetunjukRow(
                            label = "Lanjut",
                            subtitle = "Ke halaman berikutnya",
                            icon = Icons.AutoMirrored.Filled.ArrowForward,
                            iconOnRight = true,
                            onClick = onNext
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StaggeredEntrance(delayMillis = 100) {
                        PetunjukRow(
                            label = "Sampul",
                            subtitle = "Kembali ke halaman awal",
                            icon = Icons.Default.Home,
                            iconOnRight = false,
                            onClick = onHome
                        )
                    }
                    StaggeredEntrance(delayMillis = 160) {
                        PetunjukRow(
                            label = "Info",
                            subtitle = "Profil pengembang media",
                            icon = Icons.Default.Info,
                            iconOnRight = false,
                            onClick = onInfo
                        )
                    }
                    StaggeredEntrance(delayMillis = 220) {
                        PetunjukRow(
                            label = "Menu",
                            subtitle = "Daftar fitur utama",
                            icon = Icons.Default.Menu,
                            iconOnRight = false,
                            onClick = onMenu
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PetunjukRow(
    label: String,
    subtitle: String,
    icon: ImageVector,
    iconOnRight: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = CoralPink.copy(alpha = 0.85f),
        border = BorderStroke(2.5.dp, Color.White),
        shadowElevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .minimumInteractiveComponentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!iconOnRight) {
                BlackGlossyIconCircle(icon = icon, contentDescription = label)
            } else {
                Spacer(modifier = Modifier.width(10.dp))
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp),
                    color = InkNavy
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = InkNavy.copy(alpha = 0.8f)
                )
            }

            if (iconOnRight) {
                BlackGlossyIconCircle(icon = icon, contentDescription = label)
            } else {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
private fun BlackGlossyIconCircle(icon: ImageVector, contentDescription: String) {
    Surface(
        shape = CircleShape,
        color = Color(0xFF1A1A1A),
        border = BorderStroke(2.5.dp, Color.White),
        modifier = Modifier.size(50.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

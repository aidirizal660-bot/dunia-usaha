package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.SlideDestination
import com.example.model.TransitionStyle
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamCard
import com.example.ui.theme.CreamWarm
import com.example.ui.theme.FredokaFontFamily
import com.example.ui.theme.InkMuted
import com.example.ui.theme.InkNavy
import com.example.ui.theme.MeadowGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import kotlinx.coroutines.delay

@Composable
fun StaggeredEntrance(
    delayMillis: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (delayMillis > 0) delay(delayMillis.toLong())
        visible = true
    }
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(durationMillis = 360)) +
            slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                ),
                initialOffsetY = { it / 5 }
            ) +
            scaleIn(
                initialScale = 0.92f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
    ) {
        content()
    }
}

@Composable
fun LayeredDisplayTitle(
    text: String,
    fillColor: Color,
    shadowColor: Color,
    fontSize: TextUnit = 38.sp,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FredokaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = fontSize * 1.1f
            ),
            color = shadowColor,
            textAlign = textAlign,
            modifier = Modifier.offset(x = 3.dp, y = 3.dp)
        )
        Text(
            text = text,
            style = TextStyle(
                fontFamily = FredokaFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                lineHeight = fontSize * 1.1f
            ),
            color = fillColor,
            textAlign = textAlign
        )
    }
}

@Composable
fun YellowCircleNavButton(
    icon: ImageVector,
    contentDescription: String,
    testTag: String,
    size: Dp = 50.dp,
    containerColor: Color = SunAmber,
    iconTint: Color = Color.White,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "navBtnScale"
    )

    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = CircleShape,
        color = containerColor,
        shadowElevation = 5.dp,
        modifier = modifier
            .size(size)
            .minimumInteractiveComponentSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .testTag(testTag)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(size * 0.56f)
            )
        }
    }
}

@Composable
fun SlideTopNavRow(
    showMenu: Boolean = true,
    showBack: Boolean = true,
    showNext: Boolean = true,
    onMenu: () -> Unit = {},
    onBack: () -> Unit = {},
    onNext: () -> Unit = {},
    centerContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showMenu) {
                YellowCircleNavButton(
                    icon = Icons.Default.Menu,
                    contentDescription = "Menu Utama",
                    testTag = "slide_menu_button",
                    onClick = onMenu
                )
            }
            if (showBack) {
                YellowCircleNavButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    testTag = "slide_back_button",
                    onClick = onBack
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            centerContent()
        }
        Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.CenterEnd) {
            if (showNext) {
                YellowCircleNavButton(
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Lanjut",
                    testTag = "slide_next_button",
                    onClick = onNext
                )
            }
        }
    }
}

@Composable
fun DesktopTopBar(
    currentSlide: SlideDestination,
    transitionStyle: TransitionStyle,
    isFullscreen: Boolean,
    onSelectSlide: (SlideDestination) -> Unit,
    onSelectTransition: (TransitionStyle) -> Unit,
    onToggleOverview: () -> Unit,
    onToggleFullscreen: () -> Unit
) {
    var showTransitionMenu by remember { mutableStateOf(false) }

    Surface(
        color = CreamCard.copy(alpha = 0.96f),
        shadowElevation = 4.dp,
        border = BorderStroke(1.dp, InkNavy.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left brand badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SunAmber,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = "Logo Dunia Usaha",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Column {
                    Text(
                        text = "DUNIA USAHA",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FredokaFontFamily,
                            fontSize = 15.sp
                        ),
                        color = InkNavy
                    )
                    Text(
                        text = "Media Pembelajaran PGSD UNM",
                        style = MaterialTheme.typography.labelSmall,
                        color = InkMuted
                    )
                }
            }

            // Center section quick jump pills
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val quickTabs = listOf(
                    "Sampul" to SlideDestination.COVER,
                    "Menu" to SlideDestination.MENU,
                    "Materi" to SlideDestination.MATERI_MENU,
                    "Games" to SlideDestination.GAME_ARENA,
                    "Evaluasi" to SlideDestination.EVALUASI_START,
                    "Refleksi" to SlideDestination.REFLEKSI
                )
                quickTabs.forEach { (label, dest) ->
                    val isActive = when (dest) {
                        SlideDestination.MATERI_MENU -> currentSlide.section == "Materi"
                        SlideDestination.EVALUASI_START -> currentSlide.section == "Evaluasi"
                        SlideDestination.REFLEKSI -> currentSlide.section == "Penutup"
                        else -> currentSlide == dest
                    }
                    Surface(
                        onClick = { onSelectSlide(dest) },
                        shape = RoundedCornerShape(20.dp),
                        color = if (isActive) InkNavy else CreamWarm,
                        border = BorderStroke(
                            1.dp,
                            if (isActive) InkNavy else InkNavy.copy(alpha = 0.14f)
                        ),
                        modifier = Modifier
                            .height(32.dp)
                            .testTag("desktop_tab_${label.lowercase()}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            if (dest == SlideDestination.GAME_ARENA) {
                                Icon(
                                    imageVector = Icons.Default.SportsEsports,
                                    contentDescription = null,
                                    tint = if (isActive) SunGold else CoralPinkDeep,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                                color = if (isActive) Color.White else InkNavy
                            )
                        }
                    }
                }
            }

            // Right utility controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Transition selector
                Box {
                    Surface(
                        onClick = { showTransitionMenu = true },
                        shape = RoundedCornerShape(10.dp),
                        color = SkyBlue.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, SkyBlue.copy(alpha = 0.35f)),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("transition_style_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Animation,
                                contentDescription = "Pilih Animasi Transisi",
                                tint = SkyBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = transitionStyle.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = InkNavy
                            )
                        }
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

                // Slide Overview grid button
                Surface(
                    onClick = onToggleOverview,
                    shape = RoundedCornerShape(10.dp),
                    color = SunAmber.copy(alpha = 0.16f),
                    border = BorderStroke(1.dp, SunAmber.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("slide_overview_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Daftar Halaman",
                            tint = InkNavy,
                            modifier = Modifier.size(16.dp)
                        )
                        val pageText = if (currentSlide == SlideDestination.GAME_ARENA) {
                            "Games"
                        } else {
                            "${currentSlide.pageNumber} / 26"
                        }
                        Text(
                            text = pageText,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                            color = InkNavy
                        )
                    }
                }

                // Fullscreen toggle
                IconButton(
                    onClick = onToggleFullscreen,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("fullscreen_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                        contentDescription = if (isFullscreen) "Keluar Layar Penuh" else "Mode Layar Penuh",
                        tint = InkNavy
                    )
                }
            }
        }
    }
}

private val CoralPinkDeep = Color(0xFFE85A77)

@Composable
fun DesktopBottomDock(
    currentSlide: SlideDestination,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onHome: () -> Unit,
    onSelectSlide: (SlideDestination) -> Unit
) {
    val totalSlides = SlideDestination.presentationSlides.size
    val currentNum = currentSlide.pageNumber.coerceIn(1, totalSlides)
    val progress by animateFloatAsState(
        targetValue = currentNum.toFloat() / totalSlides.toFloat(),
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow),
        label = "slideProgress"
    )

    Surface(
        color = CreamCard.copy(alpha = 0.96f),
        shadowElevation = 6.dp,
        border = BorderStroke(1.dp, InkNavy.copy(alpha = 0.1f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Animated thin progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(CreamWarm)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(SunAmber)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = onHome,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("dock_home_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Ke Sampul",
                            tint = InkNavy,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        text = "${currentSlide.section} • ${currentSlide.title}",
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                        color = InkNavy,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Interactive slide dots scrubber (compact 26 pills)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    SlideDestination.presentationSlides.forEach { slide ->
                        val active = slide == currentSlide
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (active) 22.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        active -> SunAmber
                                        slide.pageNumber < currentNum -> MeadowGreen.copy(alpha = 0.6f)
                                        else -> InkNavy.copy(alpha = 0.18f)
                                    }
                                )
                                .clickable { onSelectSlide(slide) }
                        )
                    }
                }

                // Prev / Next controls + keyboard hint
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "← / →",
                        style = MaterialTheme.typography.labelSmall,
                        color = InkMuted
                    )
                    Surface(
                        onClick = onPrevious,
                        enabled = currentSlide.pageNumber > 1,
                        shape = RoundedCornerShape(8.dp),
                        color = if (currentSlide.pageNumber > 1) CreamWarm else CreamWarm.copy(alpha = 0.4f),
                        border = BorderStroke(1.dp, InkNavy.copy(alpha = 0.15f)),
                        modifier = Modifier
                            .height(30.dp)
                            .testTag("dock_prev_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Halaman Sebelumnya",
                                tint = InkNavy,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                    Surface(
                        onClick = onNext,
                        enabled = currentSlide.pageNumber < 26,
                        shape = RoundedCornerShape(8.dp),
                        color = if (currentSlide.pageNumber < 26) SunAmber else CreamWarm.copy(alpha = 0.4f),
                        modifier = Modifier
                            .height(30.dp)
                            .testTag("dock_next_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Halaman Berikutnya",
                                tint = Color.White,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SlideOverviewDialog(
    currentSlide: SlideDestination,
    onSelectSlide: (SlideDestination) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CreamBackground,
            border = BorderStroke(3.dp, InkNavy),
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .fillMaxHeight(0.84f)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Daftar Seluruh Halaman (26 Slide + Games)",
                            style = MaterialTheme.typography.headlineMedium,
                            color = InkNavy
                        )
                        Text(
                            text = "Klik halaman mana saja untuk melompat dengan transisi halus",
                            style = MaterialTheme.typography.bodyMedium,
                            color = InkMuted
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup Daftar Halaman",
                            tint = InkNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 155.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(SlideDestination.entries) { slide ->
                        val isCurrent = slide == currentSlide
                        Surface(
                            onClick = { onSelectSlide(slide) },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isCurrent) SunGold.copy(alpha = 0.35f) else CreamCard,
                            border = BorderStroke(
                                width = if (isCurrent) 2.5.dp else 1.5.dp,
                                color = if (isCurrent) SunAmber else InkNavy.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier
                                .height(74.dp)
                                .testTag("overview_slide_${slide.pageNumber}")
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (slide == SlideDestination.GAME_ARENA) CoralPink else SkyBlue
                                    ) {
                                        Text(
                                            text = if (slide == SlideDestination.GAME_ARENA) "BONUS" else "Hal ${slide.pageNumber}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Text(
                                        text = slide.section,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = InkMuted
                                    )
                                }
                                Text(
                                    text = slide.title,
                                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 12.sp),
                                    color = InkNavy,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.model.SlideDestination
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunGold
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun ProfileAnimatedSlideBackdrop(
    slide: SlideDestination,
    bitmap: ImageBitmap,
    stageW: Dp,
    stageH: Dp
) {
    var revealFullSlide by remember(slide) { mutableStateOf(false) }
    var keepSlicesMounted by remember(slide) { mutableStateOf(true) }

    LaunchedEffect(slide) {
        revealFullSlide = false
        keepSlicesMounted = true
        val revealDelay = if (slide == SlideDestination.PROFIL_TIM) 680L else 480L
        delay(revealDelay)
        revealFullSlide = true
        delay(420L)
        keepSlicesMounted = false
    }

    val baseAlpha by animateFloatAsState(
        targetValue = if (revealFullSlide) 1f else 0.16f,
        animationSpec = tween(durationMillis = 360, easing = FastOutSlowInEasing),
        label = "profileBaseAlpha"
    )
    val slicesFadeOutAlpha by animateFloatAsState(
        targetValue = if (keepSlicesMounted) 1f else 0f,
        animationSpec = tween(durationMillis = 180),
        label = "profileSlicesFadeOut"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Full slide backdrop fading cleanly to 100% once staggered pieces have landed
        Image(
            bitmap = bitmap,
            contentDescription = "Halaman ${slide.pageNumber}: ${slide.title}",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = baseAlpha }
        )

        if (slicesFadeOutAlpha > 0.01f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = slicesFadeOutAlpha }
            ) {
                // Header banner ("Profil pengembangan / PGSD UNM") drops in smoothly from top
                AnimatedBitmapSlice(
                    bitmap = bitmap,
                    xFrac = 0f,
                    yFrac = 0f,
                    wFrac = 1f,
                    hFrac = 0.335f,
                    stageW = stageW,
                    stageH = stageH,
                    delayMs = 0L,
                    startOffsetX = 0.dp,
                    startOffsetY = (-28).dp,
                    startScale = 0.96f,
                    clipShape = RoundedCornerShape(0.dp),
                    glowColor = Color.Transparent,
                    slideKey = slide
                )

                if (slide == SlideDestination.PROFIL_TIM) {
                    // 1. Left developer: Arina Aulia Ramadhani
                    AnimatedBitmapSlice(
                        bitmap = bitmap,
                        xFrac = 0.020f,
                        yFrac = 0.330f,
                        wFrac = 0.358f,
                        hFrac = 0.380f,
                        stageW = stageW,
                        stageH = stageH,
                        delayMs = 120L,
                        startOffsetX = (-56).dp,
                        startOffsetY = 12.dp,
                        startScale = 0.80f,
                        clipShape = RoundedCornerShape(36.dp),
                        glowColor = SunGold,
                        slideKey = slide
                    )
                    // 2. Right developer: Hilda Cahyani
                    AnimatedBitmapSlice(
                        bitmap = bitmap,
                        xFrac = 0.620f,
                        yFrac = 0.335f,
                        wFrac = 0.342f,
                        hFrac = 0.377f,
                        stageW = stageW,
                        stageH = stageH,
                        delayMs = 270L,
                        startOffsetX = 56.dp,
                        startOffsetY = 12.dp,
                        startScale = 0.80f,
                        clipShape = RoundedCornerShape(36.dp),
                        glowColor = SkyBlue,
                        slideKey = slide
                    )
                    // 3. Bottom-center developer: Andi Aisya Putri Andini (in front)
                    AnimatedBitmapSlice(
                        bitmap = bitmap,
                        xFrac = 0.310f,
                        yFrac = 0.610f,
                        wFrac = 0.378f,
                        hFrac = 0.362f,
                        stageW = stageW,
                        stageH = stageH,
                        delayMs = 420L,
                        startOffsetX = 0.dp,
                        startOffsetY = 52.dp,
                        startScale = 0.80f,
                        clipShape = RoundedCornerShape(36.dp),
                        glowColor = SunGold,
                        slideKey = slide
                    )
                } else if (slide == SlideDestination.PROFIL_DOSEN) {
                    // Lecturer profile card: Ibu Dosen Khotimah S.Pd., M.Pd
                    AnimatedBitmapSlice(
                        bitmap = bitmap,
                        xFrac = 0.184f,
                        yFrac = 0.358f,
                        wFrac = 0.552f,
                        hFrac = 0.544f,
                        stageW = stageW,
                        stageH = stageH,
                        delayMs = 150L,
                        startOffsetX = 0.dp,
                        startOffsetY = 48.dp,
                        startScale = 0.82f,
                        clipShape = RoundedCornerShape(44.dp),
                        glowColor = SunGold,
                        slideKey = slide
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedBitmapSlice(
    bitmap: ImageBitmap,
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp,
    delayMs: Long,
    startOffsetX: Dp,
    startOffsetY: Dp,
    startScale: Float,
    clipShape: Shape,
    glowColor: Color,
    slideKey: SlideDestination
) {
    var entered by remember(slideKey, xFrac, yFrac) { mutableStateOf(false) }
    var glowVisible by remember(slideKey, xFrac, yFrac) { mutableStateOf(false) }

    LaunchedEffect(slideKey, xFrac, yFrac) {
        entered = false
        glowVisible = false
        if (delayMs > 0L) delay(delayMs)
        entered = true
        if (glowColor != Color.Transparent) {
            delay(80L)
            glowVisible = true
            delay(260L)
            glowVisible = false
        }
    }

    val alpha by animateFloatAsState(
        targetValue = if (entered) 1f else 0f,
        animationSpec = tween(durationMillis = 280, easing = FastOutSlowInEasing),
        label = "sliceAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (entered) 1f else startScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "sliceScale"
    )
    val offsetX by animateDpAsState(
        targetValue = if (entered) 0.dp else startOffsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "sliceOffsetX"
    )
    val offsetY by animateDpAsState(
        targetValue = if (entered) 0.dp else startOffsetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "sliceOffsetY"
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (glowVisible) 0.72f else 0f,
        animationSpec = tween(durationMillis = 220),
        label = "sliceGlowAlpha"
    )

    Box(
        modifier = Modifier
            .offset(x = stageW * xFrac + offsetX, y = stageH * yFrac + offsetY)
            .size(width = stageW * wFrac, height = stageH * hFrac)
            .graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
            }
            .clip(clipShape)
            .then(
                if (glowColor != Color.Transparent && glowAlpha > 0.01f) {
                    Modifier.border(3.dp, glowColor.copy(alpha = glowAlpha), clipShape)
                } else {
                    Modifier
                }
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val srcX = (bitmap.width * xFrac).roundToInt().coerceIn(0, bitmap.width - 1)
            val srcY = (bitmap.height * yFrac).roundToInt().coerceIn(0, bitmap.height - 1)
            val srcW = (bitmap.width * wFrac).roundToInt().coerceIn(1, bitmap.width - srcX)
            val srcH = (bitmap.height * hFrac).roundToInt().coerceIn(1, bitmap.height - srcY)
            drawImage(
                image = bitmap,
                srcOffset = IntOffset(srcX, srcY),
                srcSize = IntSize(srcW, srcH),
                dstOffset = IntOffset.Zero,
                dstSize = IntSize(
                    width = size.width.roundToInt().coerceAtLeast(1),
                    height = size.height.roundToInt().coerceAtLeast(1)
                )
            )
        }
    }
}

@Composable
fun ProfileCardAccentHotspot(
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp,
    description: String,
    testTag: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val borderAlpha by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.85f
            isHovered -> 0.55f
            else -> 0f
        },
        animationSpec = tween(durationMillis = 160),
        label = "profileCardBorderAlpha"
    )
    val hoverScale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.98f
            isHovered -> 1.02f
            else -> 1f
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "profileCardHoverScale"
    )

    Box(
        modifier = Modifier
            .offset(x = stageW * xFrac, y = stageH * yFrac)
            .size(width = stageW * wFrac, height = stageH * hFrac)
            .graphicsLayer {
                scaleX = hoverScale
                scaleY = hoverScale
            }
            .clip(RoundedCornerShape(36.dp))
            .then(
                if (borderAlpha > 0.01f) {
                    Modifier.border(3.dp, Color.White.copy(alpha = borderAlpha), RoundedCornerShape(36.dp))
                } else {
                    Modifier
                }
            )
            .hoverable(interactionSource = interactionSource)
            .semantics { contentDescription = description }
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material3.ripple(color = Color.White),
                role = Role.Button,
                onClick = {}
            )
            .testTag(testTag)
    )
}

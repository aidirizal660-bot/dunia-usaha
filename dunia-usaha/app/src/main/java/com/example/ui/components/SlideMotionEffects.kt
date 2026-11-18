package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.model.SlideDestination
import com.example.ui.theme.CoralPink
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.roundToInt
import kotlin.math.sin

data class SlideSliceSpec(
    val xFrac: Float,
    val yFrac: Float,
    val wFrac: Float,
    val hFrac: Float,
    val delayMs: Long,
    val startOffsetX: Dp = 0.dp,
    val startOffsetY: Dp = 0.dp,
    val startScale: Float = 0.88f,
    val cornerDp: Dp = 24.dp
)

fun slideMotionSpecsFor(slide: SlideDestination): List<SlideSliceSpec> {
    return when (slide) {
        SlideDestination.HALLO -> listOf(
            SlideSliceSpec(0f, 0f, 1f, 0.24f, delayMs = 0L, startOffsetY = (-24).dp, startScale = 0.96f, cornerDp = 0.dp),
            SlideSliceSpec(0.01f, 0.22f, 0.44f, 0.76f, delayMs = 90L, startOffsetX = (-48).dp, startScale = 0.86f),
            SlideSliceSpec(0.44f, 0.08f, 0.54f, 0.85f, delayMs = 200L, startOffsetX = 48.dp, startScale = 0.86f)
        )
        SlideDestination.KENALAN -> listOf(
            SlideSliceSpec(0f, 0f, 1f, 0.36f, delayMs = 0L, startOffsetY = (-24).dp, startScale = 0.96f, cornerDp = 0.dp),
            SlideSliceSpec(0.08f, 0.37f, 0.41f, 0.49f, delayMs = 110L, startOffsetX = (-44).dp, startScale = 0.84f, cornerDp = 32.dp),
            SlideSliceSpec(0.51f, 0.37f, 0.41f, 0.49f, delayMs = 230L, startOffsetX = 44.dp, startScale = 0.84f, cornerDp = 32.dp)
        )
        SlideDestination.CAPAIAN -> listOf(
            SlideSliceSpec(0f, 0f, 1f, 0.24f, delayMs = 0L, startOffsetY = (-22).dp, startScale = 0.96f, cornerDp = 0.dp),
            SlideSliceSpec(0.12f, 0.23f, 0.76f, 0.72f, delayMs = 120L, startOffsetY = 44.dp, startScale = 0.86f, cornerDp = 40.dp)
        )
        SlideDestination.TUJUAN -> listOf(
            SlideSliceSpec(0f, 0f, 1f, 0.24f, delayMs = 0L, startOffsetY = (-22).dp, startScale = 0.96f, cornerDp = 0.dp),
            SlideSliceSpec(0.05f, 0.23f, 0.45f, 0.72f, delayMs = 100L, startOffsetX = (-44).dp, startScale = 0.88f),
            SlideSliceSpec(0.50f, 0.23f, 0.45f, 0.72f, delayMs = 210L, startOffsetX = 44.dp, startScale = 0.88f)
        )
        SlideDestination.MATERI_WIRAUSAHA -> listOf(
            SlideSliceSpec(0.14f, 0.04f, 0.72f, 0.35f, delayMs = 0L, startOffsetY = (-24).dp, startScale = 0.92f),
            SlideSliceSpec(0.05f, 0.40f, 0.43f, 0.57f, delayMs = 120L, startOffsetX = (-44).dp, startScale = 0.84f, cornerDp = 28.dp),
            SlideSliceSpec(0.50f, 0.40f, 0.44f, 0.57f, delayMs = 240L, startOffsetX = 44.dp, startScale = 0.84f, cornerDp = 28.dp)
        )
        SlideDestination.MATERI_SIKAP -> listOf(
            SlideSliceSpec(0.15f, 0.02f, 0.70f, 0.18f, delayMs = 0L, startOffsetY = (-20).dp, startScale = 0.95f),
            SlideSliceSpec(0.02f, 0.20f, 0.36f, 0.76f, delayMs = 100L, startOffsetX = (-42).dp, startScale = 0.86f),
            SlideSliceSpec(0.38f, 0.19f, 0.59f, 0.78f, delayMs = 210L, startOffsetX = 42.dp, startScale = 0.88f)
        )
        SlideDestination.MATERI_KOSAKATA -> listOf(
            SlideSliceSpec(0.15f, 0.02f, 0.70f, 0.20f, delayMs = 0L, startOffsetY = (-20).dp, startScale = 0.95f),
            SlideSliceSpec(0.03f, 0.21f, 0.46f, 0.76f, delayMs = 100L, startOffsetX = (-44).dp, startScale = 0.88f),
            SlideSliceSpec(0.50f, 0.21f, 0.47f, 0.76f, delayMs = 210L, startOffsetX = 44.dp, startScale = 0.88f)
        )
        SlideDestination.MATERI_IDE_POKOK -> listOf(
            SlideSliceSpec(0f, 0f, 1f, 0.17f, delayMs = 0L, startOffsetY = (-18).dp, startScale = 0.96f, cornerDp = 0.dp),
            SlideSliceSpec(0.02f, 0.17f, 0.32f, 0.80f, delayMs = 90L, startOffsetY = 34.dp, startScale = 0.90f),
            SlideSliceSpec(0.34f, 0.17f, 0.34f, 0.80f, delayMs = 180L, startOffsetY = 34.dp, startScale = 0.90f),
            SlideSliceSpec(0.68f, 0.17f, 0.30f, 0.80f, delayMs = 270L, startOffsetY = 34.dp, startScale = 0.90f)
        )
        SlideDestination.MATERI_WAWANCARA -> listOf(
            SlideSliceSpec(0f, 0f, 1f, 0.17f, delayMs = 0L, startOffsetY = (-18).dp, startScale = 0.96f, cornerDp = 0.dp),
            SlideSliceSpec(0.02f, 0.17f, 0.32f, 0.78f, delayMs = 90L, startOffsetY = 34.dp, startScale = 0.90f),
            SlideSliceSpec(0.34f, 0.17f, 0.32f, 0.80f, delayMs = 180L, startOffsetY = 34.dp, startScale = 0.90f),
            SlideSliceSpec(0.66f, 0.17f, 0.32f, 0.80f, delayMs = 270L, startOffsetY = 34.dp, startScale = 0.90f)
        )
        SlideDestination.TAHUKAH_KAMU,
        SlideDestination.KESIMPULAN -> listOf(
            SlideSliceSpec(0.03f, 0.25f, 0.28f, 0.70f, delayMs = 80L, startOffsetX = (-42).dp, startScale = 0.86f),
            SlideSliceSpec(0.31f, 0.14f, 0.65f, 0.76f, delayMs = 190L, startOffsetX = 42.dp, startScale = 0.86f, cornerDp = 32.dp)
        )
        SlideDestination.EVALUASI_START -> listOf(
            SlideSliceSpec(0.03f, 0.05f, 0.48f, 0.88f, delayMs = 70L, startOffsetX = (-42).dp, startScale = 0.88f),
            SlideSliceSpec(0.51f, 0.14f, 0.47f, 0.74f, delayMs = 180L, startOffsetX = 42.dp, startScale = 0.88f)
        )
        SlideDestination.SOAL_1,
        SlideDestination.SOAL_2,
        SlideDestination.SOAL_3,
        SlideDestination.SOAL_4 -> listOf(
            SlideSliceSpec(0.03f, 0.04f, 0.51f, 0.84f, delayMs = 60L, startOffsetX = (-38).dp, startScale = 0.90f),
            SlideSliceSpec(0.54f, 0.13f, 0.43f, 0.77f, delayMs = 160L, startOffsetX = 38.dp, startScale = 0.90f)
        )
        SlideDestination.REFLEKSI -> listOf(
            SlideSliceSpec(0.16f, 0.04f, 0.68f, 0.30f, delayMs = 0L, startOffsetY = (-22).dp, startScale = 0.94f),
            SlideSliceSpec(0.125f, 0.35f, 0.25f, 0.56f, delayMs = 100L, startOffsetY = 38.dp, startScale = 0.84f),
            SlideSliceSpec(0.375f, 0.35f, 0.25f, 0.56f, delayMs = 210L, startOffsetY = 38.dp, startScale = 0.84f),
            SlideSliceSpec(0.625f, 0.35f, 0.25f, 0.56f, delayMs = 320L, startOffsetY = 38.dp, startScale = 0.84f)
        )
        SlideDestination.DAFTAR_PUSTAKA -> listOf(
            SlideSliceSpec(0.06f, 0.08f, 0.88f, 0.84f, delayMs = 80L, startOffsetY = 32.dp, startScale = 0.90f, cornerDp = 28.dp)
        )
        else -> emptyList()
    }
}

@Composable
fun GeneralAnimatedSlideBackdrop(
    slide: SlideDestination,
    bitmap: ImageBitmap,
    stageW: Dp,
    stageH: Dp,
    slices: List<SlideSliceSpec>
) {
    var revealFullSlide by remember(slide) { mutableStateOf(false) }
    var keepSlicesMounted by remember(slide) { mutableStateOf(true) }

    val maxDelay = remember(slide) { slices.maxOfOrNull { it.delayMs } ?: 0L }

    LaunchedEffect(slide) {
        revealFullSlide = false
        keepSlicesMounted = true
        delay(maxDelay + 240L)
        revealFullSlide = true
        delay(360L)
        keepSlicesMounted = false
    }

    val baseAlpha by animateFloatAsState(
        targetValue = if (revealFullSlide) 1f else 0.20f,
        animationSpec = tween(durationMillis = 320, easing = FastOutSlowInEasing),
        label = "generalBaseAlpha"
    )
    val slicesFadeOutAlpha by animateFloatAsState(
        targetValue = if (keepSlicesMounted) 1f else 0f,
        animationSpec = tween(durationMillis = 180),
        label = "generalSlicesFadeOut"
    )

    Box(modifier = Modifier.fillMaxSize()) {
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
                slices.forEachIndexed { index, spec ->
                    AnimatedSliceItem(
                        bitmap = bitmap,
                        spec = spec,
                        stageW = stageW,
                        stageH = stageH,
                        slideKey = slide,
                        index = index
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedSliceItem(
    bitmap: ImageBitmap,
    spec: SlideSliceSpec,
    stageW: Dp,
    stageH: Dp,
    slideKey: SlideDestination,
    index: Int
) {
    var entered by remember(slideKey, index) { mutableStateOf(false) }

    LaunchedEffect(slideKey, index) {
        entered = false
        if (spec.delayMs > 0L) delay(spec.delayMs)
        entered = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (entered) 1f else 0f,
        animationSpec = tween(durationMillis = 260, easing = FastOutSlowInEasing),
        label = "genSliceAlpha"
    )
    val scale by animateFloatAsState(
        targetValue = if (entered) 1f else spec.startScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "genSliceScale"
    )
    val offsetX by animateDpAsState(
        targetValue = if (entered) 0.dp else spec.startOffsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "genSliceOffsetX"
    )
    val offsetY by animateDpAsState(
        targetValue = if (entered) 0.dp else spec.startOffsetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "genSliceOffsetY"
    )

    Box(
        modifier = Modifier
            .offset(x = stageW * spec.xFrac + offsetX, y = stageH * spec.yFrac + offsetY)
            .size(width = stageW * spec.wFrac, height = stageH * spec.hFrac)
            .graphicsLayer {
                this.alpha = alpha
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(spec.cornerDp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val srcX = (bitmap.width * spec.xFrac).roundToInt().coerceIn(0, bitmap.width - 1)
            val srcY = (bitmap.height * spec.yFrac).roundToInt().coerceIn(0, bitmap.height - 1)
            val srcW = (bitmap.width * spec.wFrac).roundToInt().coerceIn(1, bitmap.width - srcX)
            val srcH = (bitmap.height * spec.hFrac).roundToInt().coerceIn(1, bitmap.height - srcY)
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
fun QrScannerSweepOverlay(
    xFrac: Float,
    yFrac: Float,
    wFrac: Float,
    hFrac: Float,
    stageW: Dp,
    stageH: Dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "qrScanner")
    val sweepFraction by infiniteTransition.animateFloat(
        initialValue = 0.08f,
        targetValue = 0.92f,
        animationSpec = infiniteRepeatable(
            animation = tween(1900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "qrSweepPos"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "qrGlowAlpha"
    )

    val boxH = stageH * hFrac
    Box(
        modifier = Modifier
            .offset(x = stageW * xFrac, y = stageH * yFrac)
            .size(width = stageW * wFrac, height = boxH)
            .clip(RoundedCornerShape(20.dp))
            .border(2.5.dp, SkyBlue.copy(alpha = glowAlpha), RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = boxH * sweepFraction)
                .height(3.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            SkyBlue.copy(alpha = 0.85f),
                            SunGold.copy(alpha = 0.9f),
                            SkyBlue.copy(alpha = 0.85f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
fun TwinklingSparklesOverlay(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "sparkles")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2.0 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sparklePhase"
    )

    val anchors = remember {
        listOf(
            Triple(0.06f, 0.28f, SunGold),
            Triple(0.94f, 0.28f, CoralPink),
            Triple(0.05f, 0.86f, SkyBlue),
            Triple(0.95f, 0.84f, SunAmber),
            Triple(0.49f, 0.05f, SunGold)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        anchors.forEachIndexed { idx, (xf, yf, col) ->
            val pulse = (sin(phase + idx * 1.25f) + 1f) * 0.5f
            val alpha = 0.18f + pulse * 0.55f
            val r = 6f + pulse * 6f
            val cx = size.width * xf
            val cy = size.height * yf + sin(phase + idx) * 4f

            val starPath = Path().apply {
                moveTo(cx, cy - r)
                quadraticTo(cx, cy, cx + r, cy)
                quadraticTo(cx, cy, cx, cy + r)
                quadraticTo(cx, cy, cx - r, cy)
                quadraticTo(cx, cy, cx, cy - r)
                close()
            }
            drawPath(starPath, color = col.copy(alpha = alpha))
            drawCircle(
                color = Color.White.copy(alpha = alpha * 0.8f),
                radius = r * 0.25f,
                center = Offset(cx, cy)
            )
        }
    }
}

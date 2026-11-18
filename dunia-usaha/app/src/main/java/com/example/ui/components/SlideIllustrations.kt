package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.AppRating
import com.example.model.DeveloperProfile
import com.example.ui.theme.CoralPink
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamWarm
import com.example.ui.theme.InkNavy
import com.example.ui.theme.MeadowGreen
import com.example.ui.theme.MeadowMint
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SunAmber
import com.example.ui.theme.SunGold
import com.example.ui.theme.WoodBrown
import com.example.ui.theme.WoodDark
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SoftMarketBackdrop(
    alpha: Float = 0.22f,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "backdropFloat")
    val drift by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(4200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cloudDrift"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(color = CreamBackground)

        // Subtle warm hill at bottom
        drawPath(
            path = Path().apply {
                moveTo(0f, size.height * 0.86f)
                quadraticTo(
                    size.width * 0.28f, size.height * 0.78f,
                    size.width * 0.56f, size.height * 0.86f
                )
                quadraticTo(
                    size.width * 0.82f, size.height * 0.93f,
                    size.width, size.height * 0.82f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            },
            color = MeadowMint.copy(alpha = alpha * 2.2f)
        )

        // Soft market stall silhouettes in background
        val stallWidth = size.width * 0.28f
        val stallColors = listOf(CoralPink, SkyBlue, SunAmber)
        stallColors.forEachIndexed { index, tint ->
            val startX = size.width * (0.06f + index * 0.31f)
            val topY = size.height * 0.24f
            drawRoundRect(
                color = tint.copy(alpha = alpha * 0.5f),
                topLeft = Offset(startX, topY),
                size = Size(stallWidth, size.height * 0.12f),
                cornerRadius = CornerRadius(14f, 14f)
            )
            drawRect(
                color = WoodBrown.copy(alpha = alpha * 0.35f),
                topLeft = Offset(startX + 16f, topY + size.height * 0.12f),
                size = Size(10f, size.height * 0.45f)
            )
            drawRect(
                color = WoodBrown.copy(alpha = alpha * 0.35f),
                topLeft = Offset(startX + stallWidth - 26f, topY + size.height * 0.12f),
                size = Size(10f, size.height * 0.45f)
            )
        }

        // Floating soft clouds
        val cloudPositions = listOf(
            Offset(size.width * 0.18f + drift, size.height * 0.14f),
            Offset(size.width * 0.82f - drift, size.height * 0.16f)
        )
        cloudPositions.forEach { c ->
            drawCircle(color = Color.White.copy(alpha = 0.75f), radius = 26f, center = c)
            drawCircle(
                color = Color.White.copy(alpha = 0.75f),
                radius = 20f,
                center = Offset(c.x - 22f, c.y + 6f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.75f),
                radius = 18f,
                center = Offset(c.x + 22f, c.y + 6f)
            )
        }
    }
}

@Composable
fun PicketFenceStrip(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.height(78.dp)) {
        val plankW = 28f
        val gap = 10f
        val count = (size.width / (plankW + gap)).toInt().coerceAtLeast(4)

        // Horizontal rails
        drawRect(
            color = WoodDark,
            topLeft = Offset(0f, size.height * 0.36f),
            size = Size(size.width, 10f)
        )
        drawRect(
            color = WoodDark,
            topLeft = Offset(0f, size.height * 0.72f),
            size = Size(size.width, 10f)
        )

        for (i in 0 until count) {
            val x = i * (plankW + gap) + 4f
            val path = Path().apply {
                moveTo(x, size.height)
                lineTo(x, 18f)
                lineTo(x + plankW / 2f, 2f)
                lineTo(x + plankW, 18f)
                lineTo(x + plankW, size.height)
                close()
            }
            drawPath(path = path, color = WoodBrown)
            drawPath(path = path, color = WoodDark, style = Stroke(width = 2.5f))
            drawCircle(
                color = WoodDark,
                radius = 2.5f,
                center = Offset(x + plankW / 2f, size.height * 0.41f)
            )
            drawCircle(
                color = WoodDark,
                radius = 2.5f,
                center = Offset(x + plankW / 2f, size.height * 0.77f)
            )
        }
    }
}

@Composable
fun SpinningSunDecoration(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "sunSpin")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = LinearEasing)
        ),
        label = "sunAngle"
    )
    Canvas(modifier = modifier.size(76.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        rotate(angle, pivot = center) {
            for (i in 0 until 12) {
                val rad = Math.toRadians((i * 30).toDouble())
                val start = Offset(
                    center.x + (24f * cos(rad)).toFloat(),
                    center.y + (24f * sin(rad)).toFloat()
                )
                val end = Offset(
                    center.x + (34f * cos(rad)).toFloat(),
                    center.y + (34f * sin(rad)).toFloat()
                )
                drawLine(
                    color = SunAmber,
                    start = start,
                    end = end,
                    strokeWidth = 4.5f,
                    cap = StrokeCap.Round
                )
            }
        }
        drawCircle(color = SunGold, radius = 19f, center = center)
        drawCircle(color = SunAmber, radius = 19f, center = center, style = Stroke(width = 3f))
    }
}

@Composable
fun CoverMarketStallIllustration(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "stallWave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "handWave"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Wooden canopy poles
        drawRect(WoodBrown, Offset(w * 0.14f, h * 0.24f), Size(w * 0.035f, h * 0.36f))
        drawRect(WoodBrown, Offset(w * 0.825f, h * 0.24f), Size(w * 0.035f, h * 0.36f))

        // Two cheerful kids behind counter
        // Boy (left)
        val boyCenter = Offset(w * 0.34f, h * 0.41f)
        // Waving arm
        drawLine(
            color = Color(0xFFFFD1B3),
            start = Offset(boyCenter.x - w * 0.09f, boyCenter.y + h * 0.07f),
            end = Offset(boyCenter.x - w * 0.16f, boyCenter.y - h * 0.02f + waveOffset),
            strokeWidth = 14f,
            cap = StrokeCap.Round
        )
        // Boy shirt
        drawRoundRect(
            color = SunGold,
            topLeft = Offset(boyCenter.x - w * 0.09f, boyCenter.y + h * 0.04f),
            size = Size(w * 0.18f, h * 0.15f),
            cornerRadius = CornerRadius(18f, 18f)
        )
        // Apron
        drawRoundRect(
            color = InkNavy,
            topLeft = Offset(boyCenter.x - w * 0.06f, boyCenter.y + h * 0.06f),
            size = Size(w * 0.12f, h * 0.13f),
            cornerRadius = CornerRadius(10f, 10f)
        )
        // Boy face
        drawCircle(Color(0xFFFFD8BE), radius = w * 0.085f, center = boyCenter)
        drawCircle(InkNavy, radius = w * 0.085f, center = boyCenter, style = Stroke(4f))
        // Boy hair
        drawArc(
            color = InkNavy,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(boyCenter.x - w * 0.088f, boyCenter.y - w * 0.095f),
            size = Size(w * 0.176f, w * 0.13f)
        )
        // Happy cheeks & smile
        drawCircle(CoralPink.copy(alpha = 0.55f), radius = 6f, center = Offset(boyCenter.x - 16f, boyCenter.y + 8f))
        drawCircle(CoralPink.copy(alpha = 0.55f), radius = 6f, center = Offset(boyCenter.x + 16f, boyCenter.y + 8f))
        drawArc(
            color = InkNavy,
            startAngle = 15f,
            sweepAngle = 150f,
            useCenter = false,
            topLeft = Offset(boyCenter.x - 11f, boyCenter.y + 3f),
            size = Size(22f, 14f),
            style = Stroke(3.5f, cap = StrokeCap.Round)
        )

        // Girl with hijab (right)
        val girlCenter = Offset(w * 0.66f, h * 0.42f)
        drawLine(
            color = Color(0xFFFFD1B3),
            start = Offset(girlCenter.x + w * 0.09f, girlCenter.y + h * 0.07f),
            end = Offset(girlCenter.x + w * 0.16f, girlCenter.y - h * 0.02f - waveOffset),
            strokeWidth = 14f,
            cap = StrokeCap.Round
        )
        drawRoundRect(
            color = SunGold,
            topLeft = Offset(girlCenter.x - w * 0.09f, girlCenter.y + h * 0.04f),
            size = Size(w * 0.18f, h * 0.15f),
            cornerRadius = CornerRadius(18f, 18f)
        )
        // Hijab
        drawCircle(CoralPink, radius = w * 0.095f, center = girlCenter)
        drawCircle(InkNavy, radius = w * 0.095f, center = girlCenter, style = Stroke(4f))
        drawCircle(Color(0xFFFFD8BE), radius = w * 0.072f, center = Offset(girlCenter.x, girlCenter.y + 4f))
        drawCircle(CoralPink.copy(alpha = 0.55f), radius = 5f, center = Offset(girlCenter.x - 14f, girlCenter.y + 10f))
        drawCircle(CoralPink.copy(alpha = 0.55f), radius = 5f, center = Offset(girlCenter.x + 14f, girlCenter.y + 10f))
        drawArc(
            color = InkNavy,
            startAngle = 15f,
            sweepAngle = 150f,
            useCenter = false,
            topLeft = Offset(girlCenter.x - 10f, girlCenter.y + 5f),
            size = Size(20f, 12f),
            style = Stroke(3.5f, cap = StrokeCap.Round)
        )

        // Counter table
        val counterTop = h * 0.58f
        drawRoundRect(
            color = Color(0xFFFFFDF7),
            topLeft = Offset(w * 0.08f, counterTop),
            size = Size(w * 0.84f, h * 0.34f),
            cornerRadius = CornerRadius(20f, 20f)
        )
        // Pink vertical stripes on counter
        for (i in 0 until 5) {
            drawRect(
                color = CoralPink,
                topLeft = Offset(w * (0.14f + i * 0.16f), counterTop + h * 0.04f),
                size = Size(w * 0.07f, h * 0.28f)
            )
        }
        drawRoundRect(
            color = InkNavy,
            topLeft = Offset(w * 0.08f, counterTop),
            size = Size(w * 0.84f, h * 0.34f),
            cornerRadius = CornerRadius(20f, 20f),
            style = Stroke(5f)
        )
        // Table top rim
        drawRoundRect(
            color = Color(0xFFFFF4D6),
            topLeft = Offset(w * 0.05f, counterTop - 10f),
            size = Size(w * 0.90f, 22f),
            cornerRadius = CornerRadius(12f, 12f)
        )
        drawRoundRect(
            color = InkNavy,
            topLeft = Offset(w * 0.05f, counterTop - 10f),
            size = Size(w * 0.90f, 22f),
            cornerRadius = CornerRadius(12f, 12f),
            style = Stroke(4f)
        )

        // Bunting flags across counter front
        val flagColors = listOf(CoralPink, SunGold, SkyBlue, MeadowGreen)
        for (i in 0 until 7) {
            val fx = w * (0.12f + i * 0.11f)
            drawPath(
                path = Path().apply {
                    moveTo(fx, counterTop + 12f)
                    lineTo(fx + w * 0.09f, counterTop + 12f)
                    lineTo(fx + w * 0.045f, counterTop + 42f + (waveOffset * 0.4f))
                    close()
                },
                color = flagColors[i % flagColors.size]
            )
            drawPath(
                path = Path().apply {
                    moveTo(fx, counterTop + 12f)
                    lineTo(fx + w * 0.09f, counterTop + 12f)
                    lineTo(fx + w * 0.045f, counterTop + 42f + (waveOffset * 0.4f))
                    close()
                },
                color = InkNavy,
                style = Stroke(2.5f)
            )
        }

        // 3 Drinks on counter left
        val drinkColors = listOf(SunGold, CoralPink, MeadowGreen)
        drinkColors.forEachIndexed { idx, col ->
            val dx = w * (0.13f + idx * 0.09f)
            val dy = counterTop - 44f
            drawRoundRect(
                color = col,
                topLeft = Offset(dx, dy + 8f),
                size = Size(w * 0.065f, 34f),
                cornerRadius = CornerRadius(8f, 8f)
            )
            drawRoundRect(
                color = InkNavy,
                topLeft = Offset(dx, dy + 8f),
                size = Size(w * 0.065f, 34f),
                cornerRadius = CornerRadius(8f, 8f),
                style = Stroke(3f)
            )
            drawLine(
                color = InkNavy,
                start = Offset(dx + w * 0.032f, dy + 8f),
                end = Offset(dx + w * 0.042f, dy - 10f),
                strokeWidth = 3.5f,
                cap = StrokeCap.Round
            )
        }

        // Basket of snacks in center-right
        drawArc(
            color = WoodBrown,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(w * 0.45f, counterTop - 38f),
            size = Size(w * 0.22f, 52f)
        )
        drawCircle(SunAmber, radius = 11f, center = Offset(w * 0.51f, counterTop - 22f))
        drawCircle(SunGold, radius = 11f, center = Offset(w * 0.56f, counterTop - 26f))
        drawCircle(SunAmber, radius = 11f, center = Offset(w * 0.61f, counterTop - 22f))

        // Fries cup on right
        drawRoundRect(
            color = CoralPink,
            topLeft = Offset(w * 0.72f, counterTop - 36f),
            size = Size(w * 0.11f, 34f),
            cornerRadius = CornerRadius(6f, 6f)
        )
        for (f in 0..3) {
            drawLine(
                color = SunGold,
                start = Offset(w * (0.74f + f * 0.022f), counterTop - 34f),
                end = Offset(w * (0.74f + f * 0.022f), counterTop - 52f),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )
        }

        // Striped roof awning
        val awningPath = Path().apply {
            moveTo(w * 0.05f, h * 0.25f)
            lineTo(w * 0.14f, h * 0.06f)
            lineTo(w * 0.86f, h * 0.06f)
            lineTo(w * 0.95f, h * 0.25f)
            close()
        }
        drawPath(awningPath, Color(0xFFFFFDF7))
        val stripeCount = 7
        val topLeftX = w * 0.14f
        val topRightX = w * 0.86f
        val botLeftX = w * 0.05f
        val botRightX = w * 0.95f
        for (i in 0 until stripeCount step 2) {
            val t0 = i.toFloat() / stripeCount
            val t1 = (i + 1).toFloat() / stripeCount
            drawPath(
                path = Path().apply {
                    moveTo(topLeftX + (topRightX - topLeftX) * t0, h * 0.06f)
                    lineTo(topLeftX + (topRightX - topLeftX) * t1, h * 0.06f)
                    lineTo(botLeftX + (botRightX - botLeftX) * t1, h * 0.25f)
                    lineTo(botLeftX + (botRightX - botLeftX) * t0, h * 0.25f)
                    close()
                },
                color = CoralPink
            )
        }
        drawPath(awningPath, InkNavy, style = Stroke(5f, join = StrokeJoin.Round))
    }
}

@Composable
fun CandyStallIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Poles
        drawRect(WoodBrown, Offset(w * 0.25f, h * 0.24f), Size(14f, h * 0.42f))
        drawRect(WoodBrown, Offset(w * 0.84f, h * 0.24f), Size(14f, h * 0.42f))

        // Seller girl inside stall (green hijab)
        val sellerCenter = Offset(w * 0.56f, h * 0.44f)
        drawRoundRect(
            color = CoralPink,
            topLeft = Offset(sellerCenter.x - w * 0.09f, sellerCenter.y + h * 0.05f),
            size = Size(w * 0.18f, h * 0.18f),
            cornerRadius = CornerRadius(18f, 18f)
        )
        drawCircle(Color(0xFF4CA888), radius = w * 0.105f, center = sellerCenter)
        drawCircle(InkNavy, radius = w * 0.105f, center = sellerCenter, style = Stroke(4f))
        drawCircle(Color(0xFFFFD8BE), radius = w * 0.075f, center = Offset(sellerCenter.x, sellerCenter.y + 4f))
        drawArc(
            color = InkNavy,
            startAngle = 15f,
            sweepAngle = 150f,
            useCenter = false,
            topLeft = Offset(sellerCenter.x - 10f, sellerCenter.y + 6f),
            size = Size(20f, 12f),
            style = Stroke(3.5f, cap = StrokeCap.Round)
        )

        // Wooden counter
        drawRoundRect(
            color = Color(0xFFE69D53),
            topLeft = Offset(w * 0.20f, h * 0.60f),
            size = Size(w * 0.68f, h * 0.34f),
            cornerRadius = CornerRadius(16f, 16f)
        )
        drawRoundRect(
            color = InkNavy,
            topLeft = Offset(w * 0.20f, h * 0.60f),
            size = Size(w * 0.68f, h * 0.34f),
            cornerRadius = CornerRadius(16f, 16f),
            style = Stroke(4.5f)
        )

        // Candy jars
        val jarXs = listOf(0.32f, 0.52f, 0.65f, 0.77f)
        val candyColors = listOf(CoralPink, SunGold, SkyBlue, MeadowGreen)
        jarXs.forEachIndexed { idx, xFrac ->
            val jx = w * xFrac
            val jy = h * 0.52f
            drawRoundRect(
                color = Color(0xFFEAF7FF),
                topLeft = Offset(jx, jy),
                size = Size(w * 0.09f, h * 0.12f),
                cornerRadius = CornerRadius(8f, 8f)
            )
            drawRoundRect(
                color = InkNavy,
                topLeft = Offset(jx, jy),
                size = Size(w * 0.09f, h * 0.12f),
                cornerRadius = CornerRadius(8f, 8f),
                style = Stroke(3f)
            )
            drawCircle(candyColors[idx], radius = 5f, center = Offset(jx + 12f, jy + 18f))
            drawCircle(candyColors[(idx + 1) % 4], radius = 5f, center = Offset(jx + 22f, jy + 22f))
        }

        // Buyer girl on left (pink hijab & yellow dress)
        val buyerCenter = Offset(w * 0.18f, h * 0.56f)
        drawRoundRect(
            color = SunGold,
            topLeft = Offset(buyerCenter.x - w * 0.09f, buyerCenter.y + h * 0.06f),
            size = Size(w * 0.18f, h * 0.32f),
            cornerRadius = CornerRadius(20f, 20f)
        )
        drawCircle(CoralPink, radius = w * 0.095f, center = buyerCenter)
        drawCircle(InkNavy, radius = w * 0.095f, center = buyerCenter, style = Stroke(4f))
        drawCircle(Color(0xFFFFD8BE), radius = w * 0.068f, center = Offset(buyerCenter.x + 6f, buyerCenter.y + 4f))

        // Green scalloped awning
        val awning = Path().apply {
            moveTo(w * 0.16f, h * 0.25f)
            lineTo(w * 0.26f, h * 0.06f)
            lineTo(w * 0.86f, h * 0.06f)
            lineTo(w * 0.94f, h * 0.25f)
            close()
        }
        drawPath(awning, Color(0xFF4CA888))
        drawPath(awning, InkNavy, style = Stroke(4.5f, join = StrokeJoin.Round))
    }
}

@Composable
fun ShopkeeperBoyIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val center = Offset(w * 0.45f, h * 0.38f)

        // Shirt & green apron
        drawRoundRect(
            color = SunGold,
            topLeft = Offset(center.x - w * 0.18f, center.y + h * 0.08f),
            size = Size(w * 0.36f, h * 0.28f),
            cornerRadius = CornerRadius(24f, 24f)
        )
        drawRoundRect(
            color = MeadowGreen,
            topLeft = Offset(center.x - w * 0.12f, center.y + h * 0.12f),
            size = Size(w * 0.24f, h * 0.24f),
            cornerRadius = CornerRadius(12f, 12f)
        )

        // Head + cap
        drawCircle(Color(0xFFFFD8BE), radius = w * 0.15f, center = center)
        drawArc(
            color = MeadowGreen,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(center.x - w * 0.16f, center.y - w * 0.19f),
            size = Size(w * 0.32f, w * 0.22f)
        )
        drawArc(
            color = InkNavy,
            startAngle = 15f,
            sweepAngle = 150f,
            useCenter = false,
            topLeft = Offset(center.x - 16f, center.y + 8f),
            size = Size(32f, 18f),
            style = Stroke(4f, cap = StrokeCap.Round)
        )

        // Wooden counter
        drawRoundRect(
            color = WoodBrown,
            topLeft = Offset(w * 0.04f, h * 0.72f),
            size = Size(w * 0.92f, h * 0.22f),
            cornerRadius = CornerRadius(14f, 14f)
        )
        // Plant pot on left
        drawRoundRect(
            color = Color(0xFFD97B43),
            topLeft = Offset(w * 0.08f, h * 0.54f),
            size = Size(w * 0.12f, h * 0.18f),
            cornerRadius = CornerRadius(6f, 6f)
        )
        drawCircle(MeadowGreen, radius = 16f, center = Offset(w * 0.14f, h * 0.50f))
        // Snack jar & paper bag on right
        drawRoundRect(
            color = Color(0xFFC89B6E),
            topLeft = Offset(w * 0.76f, h * 0.44f),
            size = Size(w * 0.16f, h * 0.28f),
            cornerRadius = CornerRadius(8f, 8f)
        )
        drawRoundRect(
            color = Color(0xFFFFF3C4),
            topLeft = Offset(w * 0.60f, h * 0.52f),
            size = Size(w * 0.13f, h * 0.20f),
            cornerRadius = CornerRadius(8f, 8f)
        )
    }
}

@Composable
fun CuteKidBookIllustration(
    isGirl: Boolean = true,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val headCenter = Offset(w * 0.5f, h * 0.34f)

        // Body / dress or shirt
        drawRoundRect(
            color = if (isGirl) SunGold else SunAmber,
            topLeft = Offset(w * 0.24f, h * 0.52f),
            size = Size(w * 0.52f, h * 0.42f),
            cornerRadius = CornerRadius(24f, 24f)
        )
        drawRoundRect(
            color = InkNavy,
            topLeft = Offset(w * 0.24f, h * 0.52f),
            size = Size(w * 0.52f, h * 0.42f),
            cornerRadius = CornerRadius(24f, 24f),
            style = Stroke(4f)
        )

        // Hair back
        drawCircle(WoodDark, radius = w * 0.28f, center = headCenter)
        // Face
        drawCircle(Color(0xFFFFDFC4), radius = w * 0.22f, center = Offset(headCenter.x, headCenter.y + 8f))
        drawCircle(InkNavy, radius = w * 0.22f, center = Offset(headCenter.x, headCenter.y + 8f), style = Stroke(3.5f))
        // Eyes + blush + smile
        drawCircle(InkNavy, radius = 4.5f, center = Offset(headCenter.x - 16f, headCenter.y + 8f))
        drawCircle(InkNavy, radius = 4.5f, center = Offset(headCenter.x + 16f, headCenter.y + 8f))
        drawCircle(CoralPink.copy(alpha = 0.5f), radius = 6f, center = Offset(headCenter.x - 24f, headCenter.y + 18f))
        drawCircle(CoralPink.copy(alpha = 0.5f), radius = 6f, center = Offset(headCenter.x + 24f, headCenter.y + 18f))
        drawArc(
            color = InkNavy,
            startAngle = 15f,
            sweepAngle = 150f,
            useCenter = false,
            topLeft = Offset(headCenter.x - 12f, headCenter.y + 16f),
            size = Size(24f, 14f),
            style = Stroke(3.5f, cap = StrokeCap.Round)
        )

        if (isGirl) {
            // Bow clip
            drawCircle(CoralPink, radius = 7f, center = Offset(headCenter.x - w * 0.16f, headCenter.y - 10f))
        }

        // Book held in hands
        drawRoundRect(
            color = if (isGirl) Color(0xFFD96B3A) else MeadowGreen,
            topLeft = Offset(w * 0.30f, h * 0.64f),
            size = Size(w * 0.34f, h * 0.24f),
            cornerRadius = CornerRadius(8f, 8f)
        )
        drawRoundRect(
            color = InkNavy,
            topLeft = Offset(w * 0.30f, h * 0.64f),
            size = Size(w * 0.34f, h * 0.24f),
            cornerRadius = CornerRadius(8f, 8f),
            style = Stroke(3.5f)
        )
    }
}

@Composable
fun PersonAvatarBadge(
    hijabColor: Color,
    blazerColor: Color,
    hasGlasses: Boolean = false,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(72.dp)) {
        val w = size.width
        val h = size.height
        drawCircle(color = CreamWarm, radius = w * 0.48f, center = Offset(w / 2f, h / 2f))
        drawCircle(color = InkNavy.copy(alpha = 0.25f), radius = w * 0.48f, center = Offset(w / 2f, h / 2f), style = Stroke(3f))

        // Shoulders / blazer
        drawArc(
            color = blazerColor,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(w * 0.14f, h * 0.60f),
            size = Size(w * 0.72f, h * 0.52f)
        )
        // Hijab
        drawCircle(hijabColor, radius = w * 0.26f, center = Offset(w * 0.5f, h * 0.40f))
        drawCircle(InkNavy.copy(alpha = 0.4f), radius = w * 0.26f, center = Offset(w * 0.5f, h * 0.40f), style = Stroke(2.5f))
        // Face
        drawCircle(Color(0xFFFFDFC4), radius = w * 0.18f, center = Offset(w * 0.5f, h * 0.42f))
        if (hasGlasses) {
            drawCircle(InkNavy, radius = 5.5f, center = Offset(w * 0.43f, h * 0.41f), style = Stroke(2f))
            drawCircle(InkNavy, radius = 5.5f, center = Offset(w * 0.57f, h * 0.41f), style = Stroke(2f))
            drawLine(InkNavy, Offset(w * 0.485f, h * 0.41f), Offset(w * 0.515f, h * 0.41f), strokeWidth = 2f)
        } else {
            drawCircle(InkNavy, radius = 2.5f, center = Offset(w * 0.44f, h * 0.41f))
            drawCircle(InkNavy, radius = 2.5f, center = Offset(w * 0.56f, h * 0.41f))
        }
        drawArc(
            color = InkNavy,
            startAngle = 15f,
            sweepAngle = 150f,
            useCenter = false,
            topLeft = Offset(w * 0.43f, h * 0.45f),
            size = Size(w * 0.14f, h * 0.08f),
            style = Stroke(2.2f, cap = StrokeCap.Round)
        )
    }
}

@Composable
fun BoardGameTrackBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(CreamBackground)
        val tileColors = listOf(SkyBlue, SunGold, MeadowGreen, CoralPink)
        val tileCount = 10
        val tileW = size.width / tileCount
        val baseY = size.height * 0.84f
        for (i in 0 until tileCount) {
            drawRoundRect(
                color = tileColors[i % tileColors.size].copy(alpha = 0.72f),
                topLeft = Offset(i * tileW + 3f, baseY),
                size = Size(tileW - 6f, size.height * 0.14f),
                cornerRadius = CornerRadius(10f, 10f)
            )
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(i * tileW + 3f, baseY),
                size = Size(tileW - 6f, size.height * 0.14f),
                cornerRadius = CornerRadius(10f, 10f),
                style = Stroke(3f)
            )
        }
    }
}

@Composable
fun EvaluasiPropsIllustration(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val spin by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing)),
        label = "wheelSpin"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Spinner wheel
        val wheelCenter = Offset(w * 0.46f, h * 0.48f)
        val wheelRadius = minOf(w, h) * 0.28f
        val sliceColors = listOf(SkyBlue, SunGold, MeadowGreen, CoralPink, SunAmber, SkyBlue)
        rotate(spin, pivot = wheelCenter) {
            sliceColors.forEachIndexed { idx, col ->
                drawArc(
                    color = col,
                    startAngle = idx * 60f,
                    sweepAngle = 60f,
                    useCenter = true,
                    topLeft = Offset(wheelCenter.x - wheelRadius, wheelCenter.y - wheelRadius),
                    size = Size(wheelRadius * 2, wheelRadius * 2)
                )
            }
        }
        drawCircle(Color.White, radius = wheelRadius, center = wheelCenter, style = Stroke(8f))
        drawCircle(Color.White, radius = 14f, center = wheelCenter)

        // Stacked tokens (left)
        val tokenCols = listOf(CoralPink, MeadowGreen, SunGold, SkyBlue)
        tokenCols.forEachIndexed { i, col ->
            drawRoundRect(
                color = col,
                topLeft = Offset(w * 0.06f, h * (0.74f - i * 0.09f)),
                size = Size(w * 0.24f, h * 0.09f),
                cornerRadius = CornerRadius(24f, 24f)
            )
            drawRoundRect(
                color = Color.White.copy(alpha = 0.7f),
                topLeft = Offset(w * 0.06f, h * (0.74f - i * 0.09f)),
                size = Size(w * 0.24f, h * 0.09f),
                cornerRadius = CornerRadius(24f, 24f),
                style = Stroke(2.5f)
            )
        }

        // Golden Trophy (right)
        val trophyCenter = Offset(w * 0.78f, h * 0.46f)
        drawRoundRect(
            color = SunGold,
            topLeft = Offset(trophyCenter.x - w * 0.11f, h * 0.20f),
            size = Size(w * 0.22f, h * 0.34f),
            cornerRadius = CornerRadius(22f, 22f)
        )
        drawCircle(
            color = SunAmber,
            radius = w * 0.14f,
            center = Offset(trophyCenter.x - w * 0.10f, h * 0.32f),
            style = Stroke(8f)
        )
        drawCircle(
            color = SunAmber,
            radius = w * 0.14f,
            center = Offset(trophyCenter.x + w * 0.10f, h * 0.32f),
            style = Stroke(8f)
        )
        drawRect(SunAmber, Offset(trophyCenter.x - 10f, h * 0.54f), Size(20f, h * 0.14f))
        drawRoundRect(
            color = SunGold,
            topLeft = Offset(trophyCenter.x - w * 0.12f, h * 0.66f),
            size = Size(w * 0.24f, h * 0.10f),
            cornerRadius = CornerRadius(10f, 10f)
        )

        // White Dice (front-center)
        val diceTopLeft = Offset(w * 0.38f, h * 0.64f)
        drawRoundRect(
            color = Color.White,
            topLeft = diceTopLeft,
            size = Size(w * 0.16f, w * 0.16f),
            cornerRadius = CornerRadius(12f, 12f)
        )
        drawRoundRect(
            color = InkNavy.copy(alpha = 0.25f),
            topLeft = diceTopLeft,
            size = Size(w * 0.16f, w * 0.16f),
            cornerRadius = CornerRadius(12f, 12f),
            style = Stroke(3f)
        )
        val dCenter = Offset(diceTopLeft.x + w * 0.08f, diceTopLeft.y + w * 0.08f)
        drawCircle(InkNavy, radius = 4f, center = dCenter)
        drawCircle(InkNavy, radius = 4f, center = Offset(dCenter.x - 12f, dCenter.y - 12f))
        drawCircle(InkNavy, radius = 4f, center = Offset(dCenter.x + 12f, dCenter.y + 12f))
    }
}

@Composable
fun RatingFaceIllustration(
    rating: AppRating,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(88.dp)) {
        val w = size.width
        val h = size.height
        val c = Offset(w / 2f, h / 2f)

        // Leaves underneath
        drawOval(MeadowGreen, Offset(w * 0.15f, h * 0.72f), Size(w * 0.28f, h * 0.16f))
        drawOval(MeadowGreen, Offset(w * 0.57f, h * 0.72f), Size(w * 0.28f, h * 0.16f))

        when (rating) {
            AppRating.SERU -> {
                // Smiling golden star
                val starPath = Path()
                for (i in 0 until 10) {
                    val r = if (i % 2 == 0) w * 0.42f else w * 0.20f
                    val angle = Math.toRadians((i * 36 - 90).toDouble())
                    val px = c.x + (r * cos(angle)).toFloat()
                    val py = c.y + (r * sin(angle)).toFloat()
                    if (i == 0) starPath.moveTo(px, py) else starPath.lineTo(px, py)
                }
                starPath.close()
                drawPath(starPath, SunGold)
                drawPath(starPath, SunAmber, style = Stroke(4f, join = StrokeJoin.Round))
                drawCircle(InkNavy, radius = 5f, center = Offset(c.x - 12f, c.y - 4f))
                drawCircle(InkNavy, radius = 5f, center = Offset(c.x + 12f, c.y - 4f))
                drawArc(
                    color = Color(0xFFD9385A),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(c.x - 12f, c.y + 3f),
                    size = Size(24f, 16f)
                )
            }
            AppRating.KURANG -> {
                drawCircle(SunGold, radius = w * 0.36f, center = c)
                drawCircle(SunAmber, radius = w * 0.36f, center = c, style = Stroke(4f))
                drawCircle(InkNavy, radius = 5f, center = Offset(c.x - 13f, c.y - 6f))
                drawCircle(InkNavy, radius = 5f, center = Offset(c.x + 13f, c.y - 6f))
                drawLine(InkNavy, Offset(c.x - 10f, c.y + 12f), Offset(c.x + 12f, c.y + 10f), strokeWidth = 4f, cap = StrokeCap.Round)
            }
            AppRating.TIDAK_BAGUS -> {
                drawCircle(SunGold, radius = w * 0.36f, center = c)
                drawCircle(SunAmber, radius = w * 0.36f, center = c, style = Stroke(4f))
                drawCircle(InkNavy, radius = 5f, center = Offset(c.x - 13f, c.y - 4f))
                drawCircle(InkNavy, radius = 5f, center = Offset(c.x + 13f, c.y - 4f))
                drawArc(
                    color = InkNavy,
                    startAngle = 200f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(c.x - 14f, c.y + 10f),
                    size = Size(28f, 16f),
                    style = Stroke(4f, cap = StrokeCap.Round)
                )
            }
        }
    }
}

@Composable
fun QrCodeMatrixBox(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(2.5.dp, InkNavy),
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.qr_code_custom),
                contentDescription = "QR Code Kegiatan Dunia Usaha",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CelebrationConfettiLayer(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "confetti")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing)),
        label = "confettiPhase"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val colors = listOf(SunGold, CoralPink, SkyBlue, MeadowGreen, SunAmber)
        for (i in 0 until 18) {
            val x = size.width * ((i * 0.13f + 0.05f) % 0.95f)
            val y = ((size.height * ((i * 0.21f) + phase)) % size.height)
            drawCircle(
                color = colors[i % colors.size].copy(alpha = 0.65f),
                radius = 5f + (i % 4) * 2f,
                center = Offset(x, y)
            )
        }
    }
}

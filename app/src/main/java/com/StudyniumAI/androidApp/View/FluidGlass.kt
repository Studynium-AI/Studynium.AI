package com.StudyniumAI.androidApp.View

import android.graphics.Shader
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.StudyniumAI.androidApp.app.theme.Blob
import kotlin.random.Random

@Composable
fun FluidGlassPanel(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 30.dp,
    cornerRadius: Dp = 24.dp
) {
    val blobs = remember { mutableStateListOf<Blob>() }
    val transition = rememberInfiniteTransition(label = "blob_fade")
    val density = LocalDensity.current
    val context = LocalContext.current

    val blur : RenderEffect = with(density) {
        RenderEffect.createBlurEffect(
            radiusX = blurRadius.toPx(), radiusY = blurRadius.toPx(),
            edgeTreatment = Shader.TileMode.CLAMP
        )
    }

    Box(
        modifier
            .clip(RoundedCornerShape(cornerRadius))
            .graphicsLayer {
                renderEffect = blur
                alpha = 0.99f
            }
            .background(
                Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.05f)
                    )
                )
            )
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val newBlob = Blob(
                        id = Random.nextInt(),
                        position = offset,
                        radius = 40f,
                        alpha = 1f,
                        velocity = Offset.Zero
                    )
                    blobs.add(newBlob)
                }
            }
            .drawBehind {
                // Clean up faded blobs
                blobs.removeAll { it.alpha <= 0f }

                // Animate and draw blobs
                blobs.forEach { blob ->
                    // Update alpha and radius for fade-out
                    blob.alpha -= 0.015f
                    blob.radius += 1.5f

                    drawCircle(
                        color = Color.White.copy(alpha = blob.alpha * 0.2f),
                        radius = blob.radius,
                        center = blob.position,
                        blendMode = BlendMode.Plus
                    )
                }
            }
    )
}
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
import android.graphics.RenderEffect.createBlurEffect
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.StudyniumAI.androidApp.app.theme.Blob
import kotlin.random.Random
import com.StudyniumAI.androidApp.R

@Composable
fun FluidGlassPanel(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    blurRadius: Dp = 30.dp,
    cornerRadius: Dp = 24.dp
) {
    val blobs = remember { mutableStateListOf<Blob>() }
    val transition = rememberInfiniteTransition(label = "blob_fade")
    val density = LocalDensity.current
    val context = LocalContext.current

    val blur : RenderEffect = with(density) {
        createBlurEffect(
            blurRadius.toPx(),blurRadius.toPx(),
            Shader.TileMode.CLAMP
        ).asComposeRenderEffect()
    }

    Box(
        modifier
            .clip(RoundedCornerShape(cornerRadius))
            .graphicsLayer {
                renderEffect = blur
                alpha = 0.99f
            }
            .background(
                Brush.radialGradient(
                    listOf(
                        Color.White.copy(alpha = 0.05f),
                        Color.White.copy(alpha = 0.05f),
                        Color.White.copy(alpha = 0.7f)
                    ),
                    radius = with(density) { (size*3/4).toPx() }
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
            .size(size)
    )
}


@Preview
@Composable
fun FluidGlassPreview() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id = R.drawable.gradient), contentDescription = "Description of your image", modifier = Modifier.fillMaxSize())
        FluidGlassPanel(size = 200.dp)
    }
}
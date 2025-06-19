package com.StudyniumAI.androidApp.app.theme

import androidx.compose.ui.geometry.Offset

data class Blob(
    val id: Int,
    var position: Offset,
    var radius: Float,
    var alpha: Float,
    var velocity: Offset
)
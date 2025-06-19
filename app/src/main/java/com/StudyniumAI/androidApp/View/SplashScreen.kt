package com.StudyniumAI.androidApp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.StudyniumAI.androidApp.R
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0x80E0FFFF)),
        contentAlignment = Alignment.Center,
        content = {
            Image(
                painter = painterResource(id = R.drawable.studynium_ai),
                contentDescription = "Splash Screen Image"
            )
        }
    )
    val coroutineScope = rememberCoroutineScope()
    navigateToLogin(navController, coroutineScope)
}

fun navigateToLogin(navController: NavController, coroutineScope: kotlinx.coroutines.CoroutineScope) {
    coroutineScope.launch {
        delay(500)
        navController.navigate(AppDestinations.LOGIN_ROUTE)
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
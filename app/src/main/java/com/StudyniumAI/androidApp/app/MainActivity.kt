package com.StudyniumAI.androidApp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalDataSerializer
import com.StudyniumAI.androidApp.View.AuthContent
import com.StudyniumAI.androidApp.View.HomeView
import com.StudyniumAI.androidApp.View.LoginView
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations
import com.StudyniumAI.androidApp.View.RegisterView
import com.StudyniumAI.androidApp.View.SplashScreen
import android.content.Context

val Context.dataStore by dataStore(fileName = "localData.json", serializer = LocalDataSerializer)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AppDestinations.SPLASH_ROUTE
                ) {
                    composable(AppDestinations.LOGIN_ROUTE) {
                        LoginView(navController = navController)
                    }
                    composable(AppDestinations.REGISTRATION_ROUTE) {
                        RegisterView(navController = navController,dataStore = dataStore)
                    }
                    composable(AppDestinations.HOME_ROUTE) {
                        HomeView(navController = navController,dataStore = dataStore)
                    }
                    composable(AppDestinations.SPLASH_ROUTE) {
                        SplashScreen(navController = navController)
                    }
                    composable(AppDestinations.AUTH_ROUTE) {
                        AuthContent(navController = navController)
                    }
                }
            }
        }
    }
}
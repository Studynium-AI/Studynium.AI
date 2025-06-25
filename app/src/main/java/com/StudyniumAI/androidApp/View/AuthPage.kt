package com.StudyniumAI.androidApp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations
import com.StudyniumAI.androidApp.R


@Composable
fun AuthContent(navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        Column (
            modifier = Modifier.fillMaxWidth(0.8f).padding(20.dp)
        ){
            Image(painter = painterResource(id = R.drawable.studynium_ai), contentDescription = "The Studynium.AI logo", modifier = Modifier.size(250.dp).align(Alignment.CenterHorizontally))
            Text(
                text = "Welcome to Studynium AI",
                fontSize = 30.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontFamily = nationalParkFamily,
                modifier = Modifier.padding(25.dp)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = {
                    navController.navigate(AppDestinations.LOGIN_ROUTE)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = {
                    navController.navigate(AppDestinations.REGISTRATION_ROUTE)
                },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(text = "Register")
            }
        }

    }
}

@Preview
@Composable
fun AuthContentPreview() {
    AuthContent(navController = rememberNavController())
}
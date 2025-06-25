package com.StudyniumAI.androidApp.View

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.StudyniumAI.androidApp.Model.LoginData
import com.StudyniumAI.androidApp.R
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations.REGISTRATION_ROUTE
import com.StudyniumAI.androidApp.ViewModel.ViewModelAuth
import kotlinx.coroutines.launch

val nationalParkFamily = FontFamily(
    Font(R.font.nationalpark_extrabold, FontWeight.ExtraBold)
)


@Composable
fun LoginView(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {contentPadding ->
        LoginViewContent(Modifier.padding(contentPadding),snackbarHostState,navController)
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LoginViewContent(modifier: Modifier = Modifier,snackbarHostState: SnackbarHostState,navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val viewModel = ViewModelAuth()
    val scope = rememberCoroutineScope()


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Spacer(
            modifier = Modifier
                .size(50.dp)
                .fillMaxWidth()
        )
        IconButton(
            onClick = {
                navController.navigate(AppDestinations.AUTH_ROUTE)
            }
        ) {
            Image(painter = painterResource(id = R.drawable.left), contentDescription = "Back button", modifier = Modifier.size(30.dp))
        }
        Image(
            painter = painterResource(id = R.drawable.books), // Replace with your image name (without extension)
            contentDescription = "Hand drawn image of books", // Important for accessibility
            modifier = Modifier
                .size(250.dp)
                .align(alignment = Alignment.End)

        )
        Text(
            text = "Login",
            fontSize = 50.sp,
            fontFamily = nationalParkFamily,
            modifier = Modifier.padding(horizontal = 25.dp)
        )
        Text(
            text = "Start Your Journey To Academic Success With Us",
            fontSize = 20.sp,
            fontFamily = nationalParkFamily,
            modifier = Modifier.padding(horizontal = 25.dp,vertical = 15.dp)
        )
        // Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            label = { Text(text = "Username") },
            modifier = Modifier.padding(vertical = 10.dp).align(alignment = Alignment.CenterHorizontally),
            value = username,
            onValueChange = { username = it }
        )
        OutlinedTextField(
            label = { Text(text = "Password") },
            modifier = Modifier.padding(vertical = 10.dp).align(alignment = Alignment.CenterHorizontally),
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                    )
                }
            }
        )
        Button(
            onClick = {
                viewModel.loginClick(LoginData(username, password), scope, snackbarHostState, navController)
                      },
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp).align(alignment = Alignment.CenterHorizontally).fillMaxWidth()
        ) {
            Text(
                text = "Login",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        TextButton(
            onClick = {
                scope.launch {
                    showSnacky("feature is not yet implemented", snackbarHostState, scope)
                }
                      },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally).fillMaxWidth()
        ) {
            Text(
                text = "Forgot Password?",
                fontSize = 15.sp
            )
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    LoginView(navController = rememberNavController())
}
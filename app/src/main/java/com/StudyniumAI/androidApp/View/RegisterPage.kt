package com.lwmkk.androidlogin.View

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.lwmkk.androidlogin.Model.LoginData
import com.lwmkk.androidlogin.R
import com.lwmkk.androidlogin.View.Navigation.AppDestinations
import com.lwmkk.androidlogin.ViewModel.ViewModelLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterView(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {contentPadding ->
        RegisterViewContent(Modifier.padding(contentPadding),snackbarHostState,navController)
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun RegisterViewContent(modifier: Modifier = Modifier,snackbarHostState: SnackbarHostState,navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rePasswordVisibility by remember { mutableStateOf(false) }
    val viewModel = ViewModelLogin()
    val scope = rememberCoroutineScope()


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80E0FFFF))
    ) {
        Spacer(modifier = Modifier
            .size(50.dp)
            .fillMaxWidth())
        IconButton(
            onClick = {
            navController.navigate(AppDestinations.LOGIN_ROUTE)
            }
        ) {
            Image(painter = painterResource(id = R.drawable.left), contentDescription = "Back button", modifier = Modifier.size(30.dp))
        }
        Spacer(
            modifier = Modifier
                .size(150.dp)
                .fillMaxWidth()
        )
//        Image(
//            painter = painterResource(id = R.drawable.books), // Replace with your image name (without extension)
//            contentDescription = "Description of your image", // Important for accessibility
//            modifier = Modifier
//                .size(250.dp)
//                .align(alignment = Alignment.End)
//
//        )
        Text(
            text = "Register",
            fontSize = 50.sp,
            fontFamily = nationalParkFamily,
            modifier = Modifier.padding(horizontal = 25.dp)
        )
        Text(
            text = "Be A Part Of Our Family!!",
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

        OutlinedTextField(
            label = { Text(text = "Re-Enter Password") },
            modifier = Modifier.padding(vertical = 10.dp).align(alignment = Alignment.CenterHorizontally),
            value = rePassword,
            onValueChange = { rePassword = it },
            visualTransformation = if (rePasswordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { rePasswordVisibility = !rePasswordVisibility }) {
                    Icon(
                        imageVector = if (rePasswordVisibility) {
                            Icons.Filled.Visibility
                        } else {
                            Icons.Filled.VisibilityOff
                        },
                        contentDescription = if (rePasswordVisibility) "Hide password" else "Show password",
                    )
                }
            }
        )

        Button(
            onClick = {
                if (password != rePassword) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Passwords do not match")
                    }
                }
                else {
                    viewModel.registerClick(
                        LoginData(username, password),
                        scope,
                        snackbarHostState,
                        navController
                    )
                }
            },
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp).align(alignment = Alignment.CenterHorizontally).fillMaxWidth()
        ) {
            Text(
                text = "Register",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

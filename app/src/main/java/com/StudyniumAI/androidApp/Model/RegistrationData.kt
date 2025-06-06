package com.StudyniumAI.androidApp.Model

import androidx.compose.material3.SnackbarHostState
import com.StudyniumAI.androidApp.View.showSnacky
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

data class RegistrationData(
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: Int,
    val countryCode: Int,
    val gender: String,
//    val dateOfBirth: String,
) {
    val patternEmail = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    val patternPassword = Regex("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@!#$%^&*+])[A-Za-z0-9@!#$%^&*]+$")
    fun isValidRegister(snackbarHostState: SnackbarHostState,scope: CoroutineScope): Boolean {
        when {
            email.isEmpty() -> {
                showSnacky("Email cannot be empty",snackbarHostState,scope)
                return false
            }
            password.isEmpty() -> {
                showSnacky("Password cannot be empty",snackbarHostState,scope)
                return false
            }
            !patternEmail.containsMatchIn(email) -> {
                showSnacky("Invalid Email",snackbarHostState,scope)
                return false
            }
            !patternPassword.containsMatchIn(password) -> {
                showSnacky("Invalid Password",snackbarHostState,scope)
                return false
            }
//            dateOfBirth >= LocalDate.now().toString() -> {
//                showSnacky("Invalid Date of Birth",snackbarHostState,scope)
//                return false
//            }
            phoneNumber.toString().length != 10 -> {
                showSnacky("Invalid Phone Number",snackbarHostState,scope)
                return false
            }
        }
        return true
    }
}

package com.lwmkk.androidlogin.Model

data class LoginData(val email: String, val password: String) {
    val patternEmail = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    val patternPassword = Regex("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@!#$%^&*+])[A-Za-z0-9@!#$%^&*]+$")
    protected val success : Boolean = false;
    fun isValid(): Boolean {
        return (email.isNotEmpty() && password.isNotEmpty() && patternEmail.containsMatchIn(email) && patternPassword.containsMatchIn(password))
    }
}

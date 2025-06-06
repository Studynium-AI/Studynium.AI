package com.StudyniumAI.androidApp.Model

import androidx.compose.material3.SnackbarHostState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(loginData : LoginData,snackbarHostState: SnackbarHostState,scope: CoroutineScope): Result<Unit> {
        return try {
            if (!loginData.isValidLogin(snackbarHostState,scope)) {
                return Result.failure(IllegalArgumentException("Invalid email or password"))
            }
            firebaseAuth.signInWithEmailAndPassword(loginData.email, loginData.password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(registrationData: RegistrationData,snackbarHostState: SnackbarHostState,scope: CoroutineScope): Result<Unit> {
        return try {
            if (!registrationData.isValidRegister(snackbarHostState,scope)) {
                return Result.failure(IllegalArgumentException("Invalid email or password"))
            }
            firebaseAuth.createUserWithEmailAndPassword(registrationData.email, registrationData.password).await()
            val db = FirebaseFirestore.getInstance()
            db.collection("UserData").add(registrationData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout(): Result<Unit> {
        try {
            if (isUserLoggedIn()) {
                firebaseAuth.signOut()
                return Result.success(Unit)
            }
            return Result.failure(Exception("User not logged in"))
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
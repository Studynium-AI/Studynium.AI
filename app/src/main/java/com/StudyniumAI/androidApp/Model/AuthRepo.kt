package com.StudyniumAI.androidApp.Model

import androidx.compose.material3.SnackbarHostState
import com.StudyniumAI.androidApp.View.showSnacky
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalDataUtils

class AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val localDataUtils = LocalDataUtils()

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

//    suspend fun register(registrationData: RegistrationData,snackbarHostState: SnackbarHostState,scope: CoroutineScope): Result<Unit> {
//        return try {
//            if (!registrationData.isValidRegister(snackbarHostState,scope)) {
//                return Result.failure(IllegalArgumentException("Invalid email or password"))
//            }
//            firebaseAuth.createUserWithEmailAndPassword(registrationData.email, registrationData.password).await()
//            val db = FirebaseFirestore.getInstance()
//            db.collection("UserData").document(registrationData.username).set(registrationData.toHashMap()).await()
//            Result.success(Unit)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    suspend fun register(registrationData: RegistrationData, snackbarHostState: SnackbarHostState, scope: CoroutineScope): Result<Unit> {
        return try {
            if (!registrationData.isValidRegister(snackbarHostState, scope)) {
                return Result.failure(IllegalArgumentException("Invalid registration data"))
            }
            // Creating the user in Firebase Authentication
            val authResult = firebaseAuth.createUserWithEmailAndPassword(registrationData.email, registrationData.password).await()
            val userId = authResult.user?.uid
            if (userId != null) {
                val db = FirebaseFirestore.getInstance()
                db.collection("UserData").document(userId).set(registrationData.toHashMap()).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to get user ID"))
            }
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

    fun isUserLoggedIn() : Boolean {
        return firebaseAuth.currentUser != null
    }

    suspend fun fetchUserData(username: String,snackbarHostState: SnackbarHostState,scope: CoroutineScope,datrastore: androidx.datastore.core.DataStore<com.StudyniumAI.androidApp.Model.JetpackDataStore.LocalData>): RegistrationData? { // Make it suspend, return nullable
        return try {
            val db = FirebaseFirestore.getInstance()
            val documentSnapshot = db.collection("UserData").document(username).get().await()
            val registrationData = documentSnapshot.toObject(RegistrationData::class.java) // Converts to your data class
            if (registrationData != null) { localDataUtils.updateReg(registrationData.username,"",true,registrationData.countryCode,registrationData.gender,datrastore)}
            registrationData
        } catch (e: Exception) {
            showSnacky("Error fetching user data: ${e.message}",snackbarHostState,scope)
            null // Return null if fetching fails or document doesn't exist
        }
    }
}
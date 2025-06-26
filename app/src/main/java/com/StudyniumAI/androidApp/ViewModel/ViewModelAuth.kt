package com.StudyniumAI.androidApp.ViewModel

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.StudyniumAI.androidApp.Model.AuthRepository
import com.StudyniumAI.androidApp.Model.LoginData
import com.StudyniumAI.androidApp.Model.RegistrationData
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations
import com.StudyniumAI.androidApp.View.showSnacky
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModelAuth : ViewModel() {
    private val repository : AuthRepository = AuthRepository()


    fun loginClick(loginData: LoginData, scope: CoroutineScope, snackbarHostState: SnackbarHostState, navController: NavController){
        viewModelScope.launch {
            val status = repository.login(loginData,snackbarHostState,scope)
            if (status.isSuccess) {
                showSnacky("Login Successful", snackbarHostState,scope)
                delay(500)
                navController.navigate(AppDestinations.HOME_ROUTE)
            }
            else {
                showSnacky("Login Failed", snackbarHostState,scope)
            }
        }
    }

    fun registerClick(registerData: RegistrationData,scope: CoroutineScope,snackbarHostState: SnackbarHostState, navController: NavController) {
        viewModelScope.launch {
            val status = repository.register(registerData,snackbarHostState,scope)
            if (status.isSuccess) {
                showSnacky("Registration Successful, Proceed To Login", snackbarHostState,scope)
                delay(500)
                navController.navigate(AppDestinations.LOGIN_ROUTE)
            }
            else {
                showSnacky("Registration Failed", snackbarHostState,scope)
            }
        }
    }

    fun signOutClick(scope: CoroutineScope, snackbarHostState: SnackbarHostState, navController: NavController) {
        viewModelScope.launch {
            val status = repository.logout()
            if (status.isSuccess) {
                showSnacky("Sign Out Successful", snackbarHostState,scope)
                delay(500)
                navController.navigate(AppDestinations.LOGIN_ROUTE)
            }
            else {
                showSnacky("Sign Out Failed", snackbarHostState,scope)
            }
        }
    }

    fun ViewData(scope: CoroutineScope, user: String, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            val status = repository.fetchUserData(user, snackbarHostState, scope)
            if (status is RegistrationData) {
                showSnacky("Data Fetch Successful", snackbarHostState,scope)
            }
            else {
                showSnacky("Data Fetch Failed", snackbarHostState, scope)
            }
        }
    }
}

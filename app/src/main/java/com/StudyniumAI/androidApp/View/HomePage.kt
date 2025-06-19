package com.StudyniumAI.androidApp.View

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.StudyniumAI.androidApp.ViewModel.ViewModelAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController) {
    val snackbarHostState = SnackbarHostState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val viewModel : ViewModelAuth = ViewModelAuth()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Studynium.AI",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 50.sp,fontFamily = nationalParkFamily
                )
                Text(
                    text = "Empowering Your Study",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,fontFamily = nationalParkFamily
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Sign Out") },
                    selected = false,
                    onClick = {
                            viewModel.signOutClick(scope,snackbarHostState,navController)
                    },
                    icon = { Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = "Sign Out") }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(
                            text = "Home",
                            fontSize = 50.sp,
                            fontFamily = nationalParkFamily,
                            modifier = Modifier.padding(horizontal = 25.dp)
                        )
                    },
                    scrollBehavior = scrollBehavior,
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },

                    )
            },
            snackbarHost = {
                SnackbarHost(snackbarHostState)
            }
        ) { contentPadding ->
            HomeViewContent(Modifier.padding(contentPadding))
        }
    }

}

@Composable
fun HomeViewContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .size(700.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Home",
            fontSize = 50.sp,
            fontFamily = nationalParkFamily,
            modifier = Modifier.padding(horizontal = 25.dp)
            )
    }
}

fun showSnacky(status: String, snackbarHostState: SnackbarHostState, scope: CoroutineScope) {
    scope.launch {
        snackbarHostState.showSnackbar(status)
    }
}
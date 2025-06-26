package com.StudyniumAI.androidApp.View

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.StudyniumAI.androidApp.Model.RegistrationData
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
    val dialogState: DialogState = rememberDialogState()

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
    if (dialogState.isVisible) {
        DialogFn(
            dialogTitle = dialogState.title,
            dialogText = dialogState.message,
            )
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

@Preview
@Composable
fun HomeViewPreview() {
    HomeView(navController = rememberNavController())
}

@Composable
fun DialogFn(
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector = Icons.AutoMirrored.Outlined.Message,
    showConf: Boolean = true ,
    onDismissRequest: (() -> Unit)? = null,
    onConfirmation: (() -> Unit)? = null,
) {
    """
        creates a dialog box with the given parameters:
        
        onDismissRequest: function to be called when the dialog is dismissed
        onConfirmation: function to be called when the user confirms the dialog
        dialogTitle: title of the dialog
        dialogText: text to be displayed in the dialog
        icon: icon to be displayed in the dialog
        showConf: boolean to show or hide the confirmation button
    """.trimIndent()

    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            if (onDismissRequest != null) { onDismissRequest()} else {null}
        },
        confirmButton = { if (showConf == true && onConfirmation != null) {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        } else { null }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    if (onDismissRequest != null) { onDismissRequest() } else { null }
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

class DialogState {
    var isVisible by mutableStateOf(false)
    var title by mutableStateOf("")
    var message by mutableStateOf("")
    var icon by mutableStateOf<ImageVector?>(null)
    var onConfirmAction by mutableStateOf<(() -> Unit)?>(null)

    fun showDialog(
        title: String,
        message: String,
        icon: ImageVector? = Icons.AutoMirrored.Outlined.Message, // Default icon
        onConfirm: (() -> Unit)? = null,
//        onDismiss: (() -> Unit)? = null // You might want this too
    ) {
        this.title = title
        this.message = message
        this.icon = icon
        this.onConfirmAction = onConfirm
        this.isVisible = true
    }

    fun dismiss() {
        this.isVisible = false
        this.onConfirmAction = null
    }
}

@Composable
fun rememberDialogState(): DialogState = remember { DialogState() }


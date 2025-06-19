package com.StudyniumAI.androidApp.View

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.StudyniumAI.androidApp.Model.RegistrationData
import com.StudyniumAI.androidApp.R
import com.StudyniumAI.androidApp.View.Navigation.AppDestinations
import com.StudyniumAI.androidApp.ViewModel.ViewModelAuth

val countryCodes = listOf(
    93,   // Afghanistan
    355,  // Albania
    213,  // Algeria
    1,    // United States, Canada, and other NANP countries
    376,  // Andorra
    244,  // Angola
    54,   // Argentina
    374,  // Armenia
    61,   // Australia
    43,   // Austria
    994,  // Azerbaijan
    973,  // Bahrain
    880,  // Bangladesh
    375,  // Belarus
    32,   // Belgium
    501,  // Belize
    229,  // Benin
    591,  // Bolivia
    387,  // Bosnia and Herzegovina
    267,  // Botswana
    55,   // Brazil
    359,  // Bulgaria
    226,  // Burkina Faso
    257,  // Burundi
    855,  // Cambodia
    237,  // Cameroon
    1,    // Canada (NANP)
    238,  // Cape Verde
    236,  // Central African Republic
    235,  // Chad
    56,   // Chile
    86,   // China
    57,   // Colombia
    269,  // Comoros
    242,  // Congo
    506,  // Costa Rica
    385,  // Croatia
    53,   // Cuba
    357,  // Cyprus
    420,  // Czech Republic
    45,   // Denmark
    253,  // Djibouti
    1,    // Dominican Republic (NANP)
    593,  // Ecuador
    20,   // Egypt
    503,  // El Salvador
    240,  // Equatorial Guinea
    291,  // Eritrea
    372,  // Estonia
    251,  // Ethiopia
    679,  // Fiji
    358,  // Finland
    33,   // France
    241,  // Gabon
    220,  // Gambia
    995,  // Georgia
    49,   // Germany
    233,  // Ghana
    30,   // Greece
    502,  // Guatemala
    224,  // Guinea
    245,  // Guinea-Bissau
    592,  // Guyana
    509,  // Haiti
    504,  // Honduras
    36,   // Hungary
    354,  // Iceland
    91,   // India
    62,   // Indonesia
    98,   // Iran
    964,  // Iraq
    353,  // Ireland
    972,  // Israel
    39,   // Italy
    225,  // Ivory Coast
    81,   // Japan
    962,  // Jordan
    7,    // Kazakhstan
    254,  // Kenya
    686,  // Kiribati
    965,  // Kuwait
    996,  // Kyrgyzstan
    856,  // Laos
    371,  // Latvia
    961,  // Lebanon
    266,  // Lesotho
    231,  // Liberia
    218,  // Libya
    423,  // Liechtenstein
    370,  // Lithuania
    352,  // Luxembourg
    261,  // Madagascar
    265,  // Malawi
    60,   // Malaysia
    960,  // Maldives
    223,  // Mali
    356,  // Malta
    692,  // Marshall Islands
    222,  // Mauritania
    230,  // Mauritius
    52,   // Mexico
    691,  // Micronesia
    373,  // Moldova
    377,  // Monaco
    976,  // Mongolia
    382,  // Montenegro
    212,  // Morocco
    258,  // Mozambique
    95,   // Myanmar
    264,  // Namibia
    674,  // Nauru
    977,  // Nepal
    31,   // Netherlands
    64,   // New Zealand
    505,  // Nicaragua
    227,  // Niger
    234,  // Nigeria
    47,   // Norway
    968,  // Oman
    92,   // Pakistan
    680,  // Palau
    507,  // Panama
    675,  // Papua New Guinea
    595,  // Paraguay
    51,   // Peru
    63,   // Philippines
    48,   // Poland
    351,  // Portugal
    974,  // Qatar
    40,   // Romania
    7,    // Russia
    250,  // Rwanda
    685,  // Samoa
    378,  // San Marino
    239,  // São Tomé and Príncipe
    966,  // Saudi Arabia
    221,  // Senegal
    381,  // Serbia
    248,  // Seychelles
    232,  // Sierra Leone
    65,   // Singapore
    421,  // Slovakia
    386,  // Slovenia
    677,  // Solomon Islands
    252,  // Somalia
    27,   // South Africa
    82,   // South Korea
    211,  // South Sudan
    34,   // Spain
    94,   // Sri Lanka
    249,  // Sudan
    597,  // Suriname
    268,  // Eswatini
    46,   // Sweden
    41,   // Switzerland
    963,  // Syria
    886,  // Taiwan
    992,  // Tajikistan
    255,  // Tanzania
    66,   // Thailand
    228,  // Togo
    676,  // Tonga
    216,  // Tunisia
    90,   // Turkey
    993,  // Turkmenistan
    688,  // Tuvalu
    256,  // Uganda
    380,  // Ukraine
    971,  // United Arab Emirates
    44,   // United Kingdom
    1,    // United States (NANP)
    598,  // Uruguay
    998,  // Uzbekistan
    678,  // Vanuatu
    379,  // Vatican City
    58,   // Venezuela
    84,   // Vietnam
    967,  // Yemen
    260,  // Zambia
    263   // Zimbabwe
) // list of all country codes
val data = mutableMapOf<String, String>()


@Composable
fun RegisterView(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val registrationNavController = rememberNavController()
        NavHost(
            navController = registrationNavController,
            startDestination = "page 1"
        ) {
            composable("page 1") {
                RegisterPage1(snackbarHostState = snackbarHostState,navController = navController,registrationNavController = registrationNavController)
            }
            composable("page 2") {
                RegisterPage2(snackbarHostState = snackbarHostState,navController = navController,registrationNavController = registrationNavController)
            }
        }
    }

}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun RegisterPage1(snackbarHostState: SnackbarHostState,navController: NavController,registrationNavController: NavController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rePassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rePasswordVisibility by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val patternEmail = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    val patternPassword = Regex("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@!#$%^&*+])[A-Za-z0-9@!#$%^&*]+$")

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        contentPadding ->
        val modifier = Modifier.padding(contentPadding)
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
                label = { Text(text = "Email") },
                modifier = Modifier.padding(vertical = 10.dp).align(alignment = Alignment.CenterHorizontally),
                value = email,
                onValueChange = { email = it }
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
                    when {
                        email.isEmpty() -> {
                            showSnacky("Email cannot be empty", snackbarHostState, scope)
                        }

                        password.isEmpty() -> {
                            showSnacky("Password cannot be empty", snackbarHostState, scope)
                        }

                        !patternEmail.containsMatchIn(email) -> {
                            showSnacky("Invalid Email", snackbarHostState, scope)
                        }

                        !patternPassword.containsMatchIn(password) -> {
                            showSnacky("Invalid Password", snackbarHostState, scope)
                        }
                        !rePassword.equals(password) -> {
                            showSnacky("Passwords do not match", snackbarHostState, scope)
                        }
                        else -> {
                            data["username"] = username
                            data["email"] = email
                            data["password"] = password
                            registrationNavController.navigate("page 2")
                        }
                    }
                },
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp).align(alignment = Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(
                    text = "Next page!",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun RegisterPage2(modifier: Modifier = Modifier,snackbarHostState: SnackbarHostState,navController: NavController,registrationNavController: NavController) {
    //val viewModel = ViewModelAuth()
    //val scope = rememberCoroutineScope()
    var phoneNumberText by remember { mutableStateOf("0") }
    var countryCode by remember { mutableIntStateOf(1) }
    var phoneNumber : Int
    var gender by remember { mutableStateOf("") }
    val genderList = listOf<String>("MALE", "FEMALE", "OTHER")

    Scaffold (
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { contentPadding ->
        val modifier = Modifier.padding(contentPadding)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80E0FFFF))
        ) {
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .fillMaxWidth()
            )
            IconButton(
                onClick = {
                    registrationNavController.navigate("page 1")
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.left),
                    contentDescription = "Back button",
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "Lets Get To Know You A Bit More!",
                fontSize = 50.sp,
                fontFamily = nationalParkFamily,
                modifier = Modifier.padding(horizontal = 25.dp)
            )
            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                var enabled = true
                var type : MenuAnchorType = MenuAnchorType.PrimaryEditable
                var expanded = false
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(10.dp).size(100.dp,56.dp)
                ) {
                    var selectedOptionText by remember { mutableStateOf("+" + countryCodes.firstOrNull().toString()) }
                    OutlinedTextField(
                        value = selectedOptionText,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Code") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor(type, enabled) // REQUIRED for proper positioning
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        countryCodes.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption.toString()) },
                                onClick = {
                                    selectedOptionText = "+$selectionOption"
                                    expanded = false
                                    countryCode = selectionOption
                                }
                            )
                        }
                    }
                }
                OutlinedTextField(
                    label = { Text(text = "Phone Number") },
                    modifier = Modifier.padding(10.dp),
                    value = phoneNumberText,
                    onValueChange = { phoneNumberText = it }
                )
            }
            var genderExpanded = false
            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = { genderExpanded = !genderExpanded },
                modifier = Modifier.padding(10.dp).size(1000.dp,56.dp)
            ) {
                var selectedOptionText by remember { mutableStateOf(genderList.firstOrNull() ?: "") }
                OutlinedTextField(
                    value = selectedOptionText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("What is your gender?") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true) // REQUIRED for proper positioning
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    genderList.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.toString()) },
                            onClick = {
                                selectedOptionText = selectionOption
                                genderExpanded = false
                                gender = selectionOption
                            }
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    phoneNumber = phoneNumberText.toIntOrNull() ?: 0
//                    viewModel.registerClick(
//                        RegistrationData(
//                            username = data["username"].toString(),
//                            email = data["email"].toString(),
//                            password = data["password"].toString(),
//                            phoneNumber = phoneNumber,
//                            countryCode = countryCode,
//                            gender = gender,
//                        ),
//                        scope,
//                        snackbarHostState,
//                        navController
//                    )
                    },
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 50.dp).align(alignment = Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(
                    text = "Register",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }
}

@Preview
@Composable
fun RegisterPage1Preview() {
    RegisterPage1(snackbarHostState = SnackbarHostState(), navController = rememberNavController(), registrationNavController = rememberNavController())
}

@Preview
@Composable
fun RegisterPage2Preview() {
    RegisterPage2(snackbarHostState = SnackbarHostState(), navController = rememberNavController(), registrationNavController = rememberNavController())
}
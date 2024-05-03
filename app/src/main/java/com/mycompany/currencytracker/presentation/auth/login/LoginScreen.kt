package com.mycompany.currencytracker.presentation.auth.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.user.User
import com.mycompany.currencytracker.domain.model.user.UserLogin
import com.mycompany.currencytracker.presentation.auth.PasswordInput
import com.mycompany.currencytracker.presentation.auth.TextInput
import com.mycompany.currencytracker.presentation.common.ConnectionErrorDialog
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.navigation.BottomBarScreen
import com.mycompany.currencytracker.presentation.navigation.NavigationRoutes.HOME_SCREEN
import com.mycompany.currencytracker.presentation.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<LoginViewModel>()
    val stateValue = viewModel.state.value
    val stateInput = viewModel.stateUserInput.value

    val sendForm = {
        viewModel.sendLoginForm(UserLogin(stateInput.emailInput, stateInput.passwordInput))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        TextInput(
            input = stateInput.emailInput,
            onValueChange = {
                viewModel.emailOnChange(it)
            },
            placeholder = stringResource(id = R.string.enter_email),
            image = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            hasError = stateValue.error != emptyUiText,
            errorMessage = stateValue.error.asString()
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        PasswordInput(
            input = stateInput.passwordInput,
            onValueChange = {
                viewModel.passwordOnChange(it)
            },
            isPasswordShown = stateInput.isPasswordShown,
            changePasswordVisibility = viewModel::changePasswordVisibility
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(Screen.RegisterScreen.route) },
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Register")
            }
            Button(
                onClick = {
                    sendForm()
                },
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
    if (stateValue.httpError != emptyUiText)
        ConnectionErrorDialog(
            onDismissRequest = viewModel::dismissDialog,
            onConfirmation = {
                sendForm()
            },
            dialogText = stateValue.httpError.asString()
        )

    LaunchedEffect(key1 = stateValue.result) {
        if (stateValue.result != User()) {
            navController.navigate(HOME_SCREEN)
        }
    }
    BackHandler(navController.backQueue.map {it.destination.route}.contains(BottomBarScreen.Favorite.route)) {

        navController.popBackStack(BottomBarScreen.Favorite.route, true)
    }

}





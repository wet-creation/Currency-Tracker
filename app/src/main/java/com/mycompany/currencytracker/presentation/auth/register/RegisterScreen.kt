package com.mycompany.currencytracker.presentation.auth.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.presentation.auth.PasswordInput
import com.mycompany.currencytracker.presentation.auth.TextInput
import com.mycompany.currencytracker.presentation.common.ConnectionErrorDialog
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.navigation.Screen


@Composable
fun RegisterScreen(
    navController: NavController
) {

    val registerViewModel = hiltViewModel<RegisterViewModel>()
    val state = registerViewModel.state.value
    val stateUserInput = registerViewModel.stateUserInput.value
    val context = LocalContext.current
    val sendForm = {
        registerViewModel.sendRegistrationForm(
            UserRegister(
                id = "",
                email = stateUserInput.emailInput,
                password = stateUserInput.passwordInput,
                name = stateUserInput.nameInput,
                surname = stateUserInput.surnameInput,
                passwordRepeat = stateUserInput.repeatPasswordInput,
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextInput(
            input = stateUserInput.nameInput,
            placeholder = stringResource(id = R.string.enter_name),
            onValueChange = { registerViewModel.onNameChange(it) },
            image = Icons.Default.Create,
            keyboardType = KeyboardType.Text,
            hasError = state.textInputNotValid,
            errorMessage = state.formError.asString()
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        TextInput(
            input = stateUserInput.surnameInput,
            placeholder = stringResource(id = R.string.enter_surname),
            onValueChange = { registerViewModel.onSurnameChange(it) },
            image = Icons.Default.Create,
            keyboardType = KeyboardType.Text,
            hasError = state.textInputNotValid,
            errorMessage = state.formError.asString()
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        TextInput(
            input = stateUserInput.emailInput,
            onValueChange = {
                registerViewModel.onEmailChange(it)
            },
            placeholder = stringResource(id = R.string.enter_email),
            image = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            hasError = state.emailNotValid,
            errorMessage = state.formError.asString()
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        PasswordInput(
            input = stateUserInput.passwordInput,
            onValueChange = {
                registerViewModel.onPasswordChange(it)
            },
            hasError = state.passwordNotValid,
            errorMessage = state.formError.asString(),
            isPasswordShown = stateUserInput.isPasswordVisible,
            changePasswordVisibility = registerViewModel::changePasswordVisibility
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        PasswordInput(
            input = stateUserInput.repeatPasswordInput,
            onValueChange = {
                registerViewModel.onRepeatPasswordChange(it)
            },
            hasError = state.repeatPasswordNotValid,
            errorMessage = state.formError.asString(),
            isPasswordShown = stateUserInput.isPasswordVisible,
            changePasswordVisibility = registerViewModel::changePasswordVisibility
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Button(
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.outline,
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.outline,
                    disabledContentColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    sendForm()
                },
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = stringResource(id = R.string.register_text))
            }
        }
        if (state.httpError != emptyUiText) {
            ConnectionErrorDialog(
                onDismissRequest = registerViewModel::dismissDialog,
                onConfirmation = {
                    sendForm()
                },
                dialogText = state.httpError.asString()
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }


    }

    LaunchedEffect(key1 = state.isRegistry) {
        if (state.isRegistry) {
            navController.navigate(Screen.LoginScreen.route)
            Toast.makeText(context, R.string.success_register, Toast.LENGTH_LONG).show()
        }
    }
}



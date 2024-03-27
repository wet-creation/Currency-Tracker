package com.mycompany.currencytracker.presentation.auth

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.presentation.common.emptyUiString

@Preview
@Composable
fun RegisterScreen() {

    val registerViewModel = hiltViewModel<RegisterViewModel>()

    val state = registerViewModel.state.value

    var emailInput by remember {
        mutableStateOf("")
    }
    var nameInput by remember {
        mutableStateOf("")
    }
    var surnameInput by remember {
        mutableStateOf("")
    }
    var passwordInput by remember {
        mutableStateOf("")
    }
    var repeatPasswordInput by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextInput(
            input = nameInput,
            placeholder = stringResource(id = R.string.enter_name),
            onValueChange = { nameInput = it },
            image = Icons.Default.Create,
            keyboardType = KeyboardType.Text
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        TextInput(
            input = surnameInput,
            placeholder = stringResource(id = R.string.enter_surname),
            onValueChange = { surnameInput = it },
            image = Icons.Default.Create,
            keyboardType = KeyboardType.Text
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        TextInput(
            input = emailInput,
            onValueChange = {
                emailInput = it
            },
            placeholder = stringResource(id = R.string.enter_email),
            image = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        PasswordInput(
            text = passwordInput,
            onValueChange = {
                passwordInput = it
            }
        )
        HorizontalDivider(
            color = Color.Transparent,
            thickness = 16.dp
        )
        PasswordInput(
            text = repeatPasswordInput,
            onValueChange = {
                repeatPasswordInput = it
            }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    registerViewModel.sendRegistrationForm(
                        UserRegister(
                            id = "",
                            email = emailInput,
                            password = passwordInput,
                            name = nameInput,
                            surname = surnameInput,
                            passwordRepeat = repeatPasswordInput,
                        )
                    )
                },
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Register")
            }
        }
        if (state.error != emptyUiString) {
            Text(
                text = state.error.asString(),
                color = Color.Black

            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }


    }

    LaunchedEffect(key1 = state.isRegistry) {
        if (state.isRegistry)
            debugLog("navigation to login")
    }
}
package com.mycompany.currencytracker.presentation.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R

@Composable
fun TextInput(
    input: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    image: ImageVector = Icons.Default.Email,
    keyboardType: KeyboardType = KeyboardType.Text,
    hasError: Boolean = false,
    errorMessage: String = ""
) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        leadingIcon = {
            Icon(
                imageVector = image,
                contentDescription = ""
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        supportingText = { if (hasError) Text(text = errorMessage, color = Color.Red) }

    )
}

@Composable
fun PasswordInput(
    input: String = "",
    onValueChange: (String) -> Unit = {},
    hasError: Boolean = false,
    errorMessage: String = "",
    isPasswordShown: Boolean,
    changePasswordVisibility: () -> Unit
) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = stringResource(id = R.string.enter_password))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = ""
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium,
        supportingText = {
            if (hasError)
                Text(
                    text = errorMessage,
                    color = Color.Red,
                )
        },
        visualTransformation = if (!isPasswordShown) {
            PasswordVisualTransformation()
        } else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (isPasswordShown)
                R.drawable.visibility_off_icon
            else R.drawable.visibility_on_icon

            val description = if (isPasswordShown) "Hide password" else "Show password"

            IconButton(onClick = { changePasswordVisibility() }) {
                Icon(painter = painterResource(image), description)
            }


        }

    )
}
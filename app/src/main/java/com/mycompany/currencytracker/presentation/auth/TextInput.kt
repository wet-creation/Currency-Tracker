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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.mycompany.currencytracker.presentation.ui.theme.buttonsColor

@Composable
fun TextInput(
    input: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    image: ImageVector = Icons.Default.Email,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        leadingIcon = {
            Icon(
                imageVector = image,
                contentDescription = ""
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            focusedTextColor = Color.White,
            disabledTextColor = Color.Transparent,
            focusedContainerColor = buttonsColor,
            unfocusedContainerColor = buttonsColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun PasswordInput(
    text: String = "",
    onValueChange: (String) -> Unit = {},
) {
    var isPasswordShown by remember {
        mutableStateOf(false)
    }

    TextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = stringResource(id = R.string.enter_password))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = ""
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Color.Gray,
            focusedTextColor = Color.White,
            disabledTextColor = Color.Transparent,
            focusedContainerColor = buttonsColor,
            unfocusedContainerColor = buttonsColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium,
        visualTransformation = if (!isPasswordShown) {
            PasswordVisualTransformation()
        } else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            var image = if (isPasswordShown)
                R.drawable.visibility_off_icon
            else R.drawable.visibility_on_icon

            image =  R.drawable.visibility_off_icon
            val description = if (isPasswordShown) "Hide password" else "Show password"

            IconButton(onClick = { isPasswordShown = !isPasswordShown }) {
                Icon(painter = painterResource(image), description)
            }


        }

    )
}
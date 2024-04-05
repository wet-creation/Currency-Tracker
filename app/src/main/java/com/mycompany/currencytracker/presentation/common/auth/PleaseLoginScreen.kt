package com.mycompany.currencytracker.presentation.common.auth

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.navigation.Screen


@Composable
fun PleaseLoginScreen(navController: NavHostController) {
    Text(text = stringResource(id = R.string.please_login), modifier = Modifier.clickable {
        navController.navigate(Screen.LoginScreen.route)
    })

}
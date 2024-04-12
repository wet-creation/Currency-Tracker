package com.mycompany.currencytracker.presentation.setting_screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.auth.PleaseLoginScreen
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileScreen(navController: NavHostController) {
    val dataStore = StoreUserSetting(LocalContext.current)

    if (dataStore.getUser().email != "") {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Email")
                Text(text = dataStore.getUser().email)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.password_text))
            }

            Button(onClick = {
                runBlocking {
                    dataStore.logoutUser()
                }
            }) {
                Text(text = "Logout")
            }
        }
    } else {
        PleaseLoginScreen(navController)
    }
}
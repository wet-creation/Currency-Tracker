package com.mycompany.currencytracker.presentation.setting_screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.auth.PleaseLoginScreen
import com.mycompany.currencytracker.presentation.navigation.BottomBarScreen
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileScreen(navController: NavHostController) {
    val dataStore = StoreUserSetting(LocalContext.current)

    if (dataStore.getUser().email != "") {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(text = "Email")
                Text(text = dataStore.getUser().email)
            }
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(text = stringResource(id = R.string.name_text))
                Text(text = dataStore.getUser().name)
            }
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(text = stringResource(id = R.string.surname_text))
                Text(text = dataStore.getUser().surname)
            }
            HorizontalDivider()
            Button(
                colors = ButtonColors(containerColor = MaterialTheme.colorScheme.outline,
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.outline,
                    disabledContentColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                onClick = {
                    navController.popBackStack(BottomBarScreen.Home.route, false)
                    runBlocking {
                        dataStore.logoutUser()
                    }
                }) {
                Text(text = stringResource(id = R.string.logout))
            }
        }
    } else {
        PleaseLoginScreen(navController)
    }
}
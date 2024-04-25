package com.mycompany.currencytracker.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.ktx.messaging
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.data.remote.dto.user.UserLoginDto
import com.mycompany.currencytracker.domain.repository.UserRepository
import com.mycompany.currencytracker.presentation.navigation.MainScreen
import com.mycompany.currencytracker.presentation.ui.theme.CurrencyTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var userSetting: StoreUserSetting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            CurrencyTrackerTheme {
                MainScreen()
            }
        }
    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }


    override fun onStart() {
        super.onStart()
        com.google.firebase.ktx.Firebase.messaging.token.addOnCompleteListener {
            if (!it.isSuccessful)
                return@addOnCompleteListener
            val user = userSetting.getUser()
            if (user.id.isEmpty())
                return@addOnCompleteListener
            val userDto =  UserLoginDto(
                user.email,
                user.password,
                it.result
            )
            CoroutineScope(Dispatchers.IO).launch {
                userRepository.login(
                    userDto
                )

            }
        }

    }
}
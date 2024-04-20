package com.mycompany.currencytracker.data.remote.services.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.data.remote.dto.user.UserLoginDto
import com.mycompany.currencytracker.domain.repository.UserRepository
import com.mycompany.currencytracker.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class FirebaseMessageService
    : FirebaseMessagingService() {

    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var userSetting: StoreUserSetting

    companion object {
        const val CHANNEL_ID = "channel"
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val user = userSetting.getUser()
        if (user.id.isNotEmpty())
            CoroutineScope(Dispatchers.Default).launch {
                userRepository.login(
                    UserLoginDto(
                        user.email,
                        user.password,
                        token
                    )
                )
            }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        debugLog(message.data.toString())
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        createNotificationChannel(notificationManager)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(message.data["name"])
            .setContentText("The ${message.data["symbol"]} is now ${message.data["current_price"]}")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)

    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "My channel description"
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)
    }
}
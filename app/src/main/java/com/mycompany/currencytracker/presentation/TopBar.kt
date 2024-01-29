package com.mycompany.currencytracker.presentation

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val imageName = "flags/AED"

    TopAppBar(
        modifier = Modifier.padding(horizontal = 10.dp),
        title = {
            Text(
                "Currency tracker",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(600),
                    color = mainTextColor
                )
            )
        },
        navigationIcon = {
            Image(
                modifier = Modifier
                    .padding(end = 2.dp)
                    .width(23.dp)
                    .height(23.dp),
                painter = painterResource(id = getResourceId(imageName, "drawable", LocalContext.current.packageName)),
                contentDescription = "logo"
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    modifier = Modifier
                        .padding(0.03.dp)
                        .width(24.dp)
                        .height(24.dp),
                    painter = painterResource(id = R.drawable.notification_icon),
                    contentDescription = "notification",
                    tint = Color(0xFFFFFFFF)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    painter = painterResource(id = R.drawable.notification_icon),
                    contentDescription = "notification"
                )
            }
        }
    )
}

@Composable
fun getResourceId(resourceName: String, resourceType: String, packageName: String): Int {
    return LocalContext.current.resources.getIdentifier(resourceName, resourceType, packageName)
}
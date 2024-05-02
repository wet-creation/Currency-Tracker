package com.mycompany.currencytracker.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor
import com.mycompany.currencytracker.presentation.ui.theme.secondBackColor
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor

@Composable
fun ErrorScreen(
    error: String,
    onClick: () -> Unit
){
    Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(id = R.drawable.error_dark_theme), contentDescription = "error")
            Text(
                modifier = Modifier.padding(top = 40.dp, bottom = 20.dp),
                text = error,
                style = MaterialTheme.typography.displayMedium,
                color = mainTextColor
            )
            Button(
                modifier = Modifier
                    .width(158.dp)
                    .height(46.dp)
                    .background(color = selectTextColor, shape = RoundedCornerShape(size = 16.dp)),
                onClick = { onClick() },
                colors = ButtonDefaults.buttonColors(containerColor = selectTextColor)
            ) {
                Text(
                    text = stringResource(R.string.retry_text),
                    style = MaterialTheme.typography.displayMedium,
                    color = secondBackColor
                )
            }
        }
    }
}
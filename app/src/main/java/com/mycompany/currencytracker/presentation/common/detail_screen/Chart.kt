package com.mycompany.currencytracker.presentation.common.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chart(){
    Row(modifier = Modifier.padding(top = 20.dp)){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(color = Color(0xFFD9D9D9))
        ){}
    }

}
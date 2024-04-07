package com.mycompany.currencytracker.presentation.calculator.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mycompany.currencytracker.presentation.ui.theme.darkBackgroundColor
import com.mycompany.currencytracker.presentation.ui.theme.mainTextColor

@Preview
@Composable
fun ConvertButton(
    modifier: Modifier = Modifier,
    text: String = "-1",
    icon: Int = 0,
    background: Color = darkBackgroundColor,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable {
                onClick()
            }
            .then(modifier)
            .fillMaxHeight()
            .clip(RoundedCornerShape(50.dp))
    ) {
        if (text != "-1")
            Text(
                text = text,
                fontSize = 36.sp,
                color = mainTextColor
            )
        else
            Icon(painter = painterResource(id = icon), contentDescription = "", tint = Color.White)
    }

}
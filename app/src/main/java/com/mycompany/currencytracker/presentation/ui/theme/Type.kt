package com.mycompany.currencytracker.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight(400),
        color = mainTextColor
    ),
    displayMedium = TextStyle(
        fontSize = 20.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight(400),
        color = mainTextColor
    ),
    displayLarge = TextStyle(
        fontSize = 28.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight(600),
        color = mainTextColor
    ),
    displaySmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight(400),
        color = mainTextColor
    ),
    titleLarge = TextStyle(
        fontSize = 32.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight(600),
        color = mainTextColor
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

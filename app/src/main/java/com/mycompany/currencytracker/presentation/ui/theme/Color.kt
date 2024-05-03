package com.mycompany.currencytracker.presentation.ui.theme

import androidx.compose.ui.graphics.Color

//dark theme colors
val bottomBarColorDark = Color(0xFF111110)
val backgroundColorDark = Color(0xFF212121)
val mainTextColorDark = Color(0xFFFFFFFF)
val secondTextColorDark = Color(0xFF999999)
val selectColorDark = Color(0xFFF0B513)
val filterButtonsDark = Color(0xFFAFA17C)
val elementBackgroundColorDark = Color(0xFF3D362D)

//light theme colors
val bottomBarColorLight = Color(0xFFF6F6F6)
val backgroundColorLight = Color(0xFFFFFFFF)
val mainTextColorLight = Color(0xFF0D0D0D)
val secondTextColorLight = Color(0xFF828282)
val selectColorLight = Color(0xFFF0B513)
val filterButtonsLight = Color(0xFFE8BC4B)
val elementBackgroundColorLight = Color(0xFFDADADA)


val rateUpColor = Color(0xFF12C163)
val rateDownColor = Color(0xFFFF0000)
val borderColor = Color(0xFF6F6D6D)

sealed class ThemeColors(
    val primaryContainer: Color,
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val outline: Color,
    val surfaceVariant: Color,
    val secondaryContainer: Color
) {
    object Dark: ThemeColors(
        primaryContainer = bottomBarColorDark,
        background = backgroundColorDark,
        primary = mainTextColorDark,
        secondary = secondTextColorDark,
        outline = selectColorDark,
        surfaceVariant = filterButtonsDark,
        secondaryContainer = elementBackgroundColorDark
    )
    object Light: ThemeColors(
        primaryContainer = bottomBarColorLight,
        background = backgroundColorLight,
        primary = mainTextColorLight,
        secondary = secondTextColorLight,
        outline = selectColorLight,
        surfaceVariant = filterButtonsLight,
        secondaryContainer = elementBackgroundColorLight
    )
}




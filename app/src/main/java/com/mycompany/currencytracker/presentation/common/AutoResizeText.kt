package com.mycompany.currencytracker.presentation.common

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AutoResizeText(
    modifier: Modifier = Modifier,
    text: String,
    softWrap: Boolean = true,
    textAlign: TextAlign = TextAlign.Start,
    fontSizeRange: FontSizeRange,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    var fontSizeValue by remember { mutableFloatStateOf(fontSizeRange.max.value) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        color = color,
        style = style,
        textAlign = textAlign,
        softWrap = softWrap,
        fontStyle = fontStyle,
        fontSize = fontSizeValue.sp,
        onTextLayout = {
            if (it.didOverflowHeight && !readyToDraw) {
                val nextFontSizeValue = fontSizeValue - fontSizeRange.step.value
                if (nextFontSizeValue <= fontSizeRange.min.value) {
                    // Reached minimum, set minimum font size and it's readToDraw
                    fontSizeValue = fontSizeRange.min.value
                    readyToDraw = true
                } else {
                    // Text doesn't fit yet and haven't reached minimum text range, keep decreasing
                    fontSizeValue = nextFontSizeValue
                }
            } else {
                // Text fits before reaching the minimum, it's readyToDraw
                readyToDraw = true
            }
        },
        modifier = modifier.drawWithContent { if (readyToDraw) drawContent() }
    )
}

data class FontSizeRange(
    val min: TextUnit,
    val max: TextUnit,
    val step: TextUnit = DEFAULT_TEXT_STEP,
) {
    init {
        require(min < max) { "min should be less than max, $this" }
        require(step.value > 0) { "step should be greater than 0, $this" }
    }

    companion object {
        private val DEFAULT_TEXT_STEP = 0.5.sp
    }
}

@Composable
fun measureTextWidth(text: String, style: TextStyle): Dp {
    val textMeasurer = rememberTextMeasurer()
    val widthInPixels = textMeasurer.measure(text, style).size.width
    return with(LocalDensity.current) { widthInPixels.toDp() }
}

@Composable
fun DynamicText(
    modifier: Modifier = Modifier,
    text: String,
    softWrap: Boolean = true,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    style: TextStyle = LocalTextStyle.current,
) {
    var fontSize by remember { mutableStateOf(48.sp) } // Начальный размер шрифта

    BoxWithConstraints {
        Text(
            text = text,
            color = color,
            style = style,
            textAlign = textAlign,
            softWrap = softWrap,
            fontStyle = fontStyle,
            modifier = Modifier.padding(16.dp)
        )
        val maxWidth = maxWidth.value
        val textWidth = with(LocalDensity.current) { fontSize.toDp() * text.length }

        // Изменяем размер шрифта, если текст не помещается или есть место для увеличения шрифта
        if (textWidth.value > maxWidth) {
            fontSize *= maxWidth / textWidth.value
        } else if (fontSize < 40.sp) { // Максимальный размер шрифта (может быть настраиваемым)
            fontSize *= 1.1f // Увеличиваем шрифт на 10%
        }
    }
}
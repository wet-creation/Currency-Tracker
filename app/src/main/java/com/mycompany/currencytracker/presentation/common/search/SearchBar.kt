package com.mycompany.currencytracker.presentation.common.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListSearchState

@Composable
fun SearchBar(searchState: CurrencyListSearchState) {
    TextField(
        value = searchState.inputText,
        onValueChange = searchState.inputTextAction,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        leadingIcon = {
            Image(
                painter = painterResource(
                    id = R.drawable.search_icon
                ),
                contentDescription = "icon",
                modifier = Modifier
                    .padding(0.03.dp)
                    .width(24.dp)
                    .height(24.dp)
            )
        },
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.labelMedium
    )
}
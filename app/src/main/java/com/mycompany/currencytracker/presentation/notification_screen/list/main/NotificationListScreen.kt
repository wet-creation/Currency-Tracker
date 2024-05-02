package com.mycompany.currencytracker.presentation.notification_screen.list.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.domain.model.user.notification.UserNotification
import com.mycompany.currencytracker.presentation.common.bounceClick
import com.mycompany.currencytracker.presentation.common.currency.crypto.calculateDecimalPlaces
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.favorite_list.ui.HiddenRowEnd
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.secondBackColor
import com.mycompany.currencytracker.presentation.ui.theme.secondTextColor
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationListScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<NotificationListScreenViewModel>()
    val isInitialized = remember { mutableStateOf(false) }

    SideEffect {
        if (!isInitialized.value) {
            isInitialized.value = true
            viewModel.getItems()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ItemsListScreen(
            stateValue = viewModel.state.value,
            list = viewModel.state.value.items,
            modifier = Modifier
                .padding(21.dp)
                .zIndex(100f),
            onListRefresh = viewModel::getItems
        ) { item, _ ->
            RevealSwipe(
                maxRevealDp = 60.dp,
                backgroundCardEndColor = MaterialTheme.colorScheme.background,
                directions = setOf(RevealDirection.EndToStart),
                hiddenContentEnd = {
                    HiddenRowEnd()
                },
                onBackgroundEndClick = {
                    viewModel.delete(item)
                }
            ) {
                NotificationItem(notification = item, baseCurrency = viewModel.baseCurrency) {
                    navController.navigate(Screen.NotificationScreen.route + "/${item.symbol}")
                }
            }
        }
        if (viewModel.state.value.items.isEmpty() && !viewModel.state.value.isLoading && viewModel.state.value.error == emptyUiText) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_notification_img),
                    contentDescription = "No notification yet",
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .zIndex(1f)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.follow_the_prices_of_cryptocurrency),
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(400),
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.get_notification_when_prices_reach_your_targets),
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight(400),
                            color = Color.Gray,
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    YellowButton(text = stringResource(id = R.string.add_a_price_allert)) {
                        navController.navigate(Screen.NotificationScreenSelect.route)
                    }
                }
            }
        }
    }

}

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: UserNotification,
    baseCurrency: String,
    onClick: (UserNotification) -> Unit
) {
    Column(
        modifier = modifier
            .padding(bottom = 40.dp)
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            AsyncImage(
                model = notification.image,
                contentDescription = notification.symbol,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = notification.name,
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = Color.White,
                ),
            )
            Text(
                text = notification.symbol.uppercase(),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = secondTextColor,
                )
            )
            Text(
                text = stringResource(id = R.string.add),
                textAlign = TextAlign.End,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight(400),
                    color = selectTextColor
                ),
                modifier = Modifier
                    .clickable { onClick(notification) }
                    .fillMaxWidth()
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp, vertical = 8.dp)
        ) {
            if (notification.isMoreThanTarget) {
                Image(
                    painter =
                    painterResource(id = R.drawable.arrow_above_icon),
                    contentDescription = "Arrow"
                )
                Text(
                    text = stringResource(id = R.string.above),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = baseCurrency.uppercase(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White
                    )
                )
                Text(
                    text = calculateDecimalPlaces(notification.target),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White
                    )
                )
            } else {
                Image(
                    painter =
                    painterResource(id = R.drawable.arrow_below_icon),
                    contentDescription = "Arrow"
                )
                Text(
                    text = stringResource(id = R.string.below),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = baseCurrency,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White
                    )
                )
                Text(
                    text = calculateDecimalPlaces(notification.target),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White
                    )
                )
            }
        }

    }
}

@Composable
fun YellowButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .bounceClick()
            .background(
                color = selectTextColor,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .clickable { onClick() }) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight(700),
                color = secondBackColor
            )
        )
    }
}
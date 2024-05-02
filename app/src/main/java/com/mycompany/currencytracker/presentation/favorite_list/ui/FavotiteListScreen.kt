package com.mycompany.currencytracker.presentation.favorite_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.presentation.common.auth.PleaseLoginScreen
import com.mycompany.currencytracker.presentation.common.currency.CurrenciesListHeader
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListItem
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListItem
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.favorite_list.FavoriteCryptoListViewModel
import com.mycompany.currencytracker.presentation.favorite_list.FavoriteFiatListViewModel
import com.mycompany.currencytracker.presentation.navigation.Screen
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import de.charlex.compose.RevealDirection
import de.charlex.compose.RevealSwipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteListScreen(navController: NavHostController) {
    val cryptoListViewModel = hiltViewModel<FavoriteCryptoListViewModel>()
    val fiatListViewModel = hiltViewModel<FavoriteFiatListViewModel>()
    val userSetting = StoreUserSetting(LocalContext.current)

    if (userSetting.getUser().email != "") {
        CurrencyListsScreen(
            fiatListScreen = {
                ItemsListScreen(
                    header = { CurrenciesListHeader() },
                    stateValue = fiatListViewModel.state.value,
                    list = fiatListViewModel.state.value.items,
                    onListRefresh = fiatListViewModel::getItems
                ) { fiat, currNumber ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        RevealSwipe(
                            maxRevealDp = 60.dp,
                            backgroundCardEndColor = MaterialTheme.colorScheme.background,
                            directions = setOf(RevealDirection.EndToStart),
                            hiddenContentEnd = {
                                HiddenRowEnd()
                            },
                            onBackgroundEndClick = {
                                fiatListViewModel.delete(fiat)
                            }
                        ) {
                            FiatListItem(fiat, currNumber) {
                                navController.navigate(Screen.CurrencyDetailScreen.route + "/${fiat.symbol}")
                            }

                        }

                    }

                }
            }, cryptoListScreen = {
                ItemsListScreen(
                    header = { CurrenciesListHeader() },
                    stateValue = cryptoListViewModel.state.value,
                    list = cryptoListViewModel.state.value.items,
                    onListRefresh = cryptoListViewModel::getItems,
                    key = { _, item -> item.symbol }
                ) { crypto, currNumber ->
                    RevealSwipe(
                        maxRevealDp = 60.dp,
                        backgroundCardEndColor = MaterialTheme.colorScheme.background,
                        directions = setOf(RevealDirection.EndToStart),
                        hiddenContentEnd = {
                            HiddenRowEnd()
                        },
                        onBackgroundEndClick = {
                            cryptoListViewModel.delete(crypto)
                        }
                    ) {
                        CryptoListItem(crypto = crypto, number = currNumber) {
                            navController.navigate(Screen.CryptoDetailScreen.route + "/${crypto.symbol}")
                        }
                    }
                }
            })
        { title ->

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = selectTextColor,
                )
            )
        }
    } else {
        PleaseLoginScreen(navController)
    }
}

@Composable
fun HiddenRowEnd() {
    Row(horizontalArrangement = Arrangement.End) {
        Box(
            Modifier
                .background(Color.Red)
                .height(60.dp)
                .width(60.dp)) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "favorite",
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .align(
                        Alignment.Center
                    ),
                tint = Color.White
            )
        }
    }
}

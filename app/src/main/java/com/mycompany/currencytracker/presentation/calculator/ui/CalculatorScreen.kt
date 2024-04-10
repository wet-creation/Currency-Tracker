package com.mycompany.currencytracker.presentation.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.presentation.calculator.CalculatorViewModel
import com.mycompany.currencytracker.presentation.calculator.states.ActionInput
import com.mycompany.currencytracker.presentation.calculator.ui.elements.ConvertButton
import com.mycompany.currencytracker.presentation.calculator.ui.elements.ConvertItem
import com.mycompany.currencytracker.presentation.calculator.ui.elements.CryptoListItem
import com.mycompany.currencytracker.presentation.calculator.ui.elements.FiatListItem
import com.mycompany.currencytracker.presentation.common.ConnectionErrorDialog
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreen
import com.mycompany.currencytracker.presentation.common.currency.CurrencyListsScreenState
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoSearchListViewModel
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.ItemsListScreen
import com.mycompany.currencytracker.presentation.common.search.SearchPosition
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val viewModel = hiltViewModel<CalculatorViewModel>()
    val fiatSearchListViewModel = hiltViewModel<FiatSearchListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoSearchListViewModel>()
    val state = viewModel.screenState.value
    val firstRowState = state.firstRowState
    val secondRowState = state.secondRowState
    val sheetScaffoldState = rememberModalBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetScaffoldState
    )
    var searchQuery by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    var isFistRowOpen by remember {
        mutableStateOf(false)
    }

    BottomSheetScaffold(
        sheetContent = {
            CurrencyListsScreen(
                searchPosition = SearchPosition.InBetween,
                currencyListsScreenState = CurrencyListsScreenState(searchQuery) {
                    searchQuery = it
                },
                fiatListScreen = {
                    ItemsListScreen(
                        viewModel = fiatSearchListViewModel,
                        list = fiatSearchListViewModel.searchResult.value,
                        itemContent = { currencyItem, _ ->
                            FiatListItem(currencyItem) {
                                    viewModel.setRowState(currencyItem, !isFistRowOpen)
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                        })
                },
                cryptoListScreen = {
                    ItemsListScreen(
                        viewModel = cryptoSearchListViewModel,
                        list = cryptoSearchListViewModel.searchResult.value,
                        itemContent = { crypto, _ ->
                            CryptoListItem(crypto) {
                                viewModel.setRowState(crypto, !isFistRowOpen)
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                        }
                    )
                }) { title ->
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = selectTextColor,
                    )
                )
            }
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            if (state.error != emptyUiText) {
                ConnectionErrorDialog(
                    onDismissRequest = viewModel::dismissDialog,
                    onConfirmation = viewModel::initStates,
                    dialogText = state.error.asString()
                )
            } else if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            ConvertItem(
                firstRowState,
                contentDescription = "img ${firstRowState.symbol}"
            ) {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                    isFistRowOpen = true
                }
            }
            Row(
                horizontalArrangement = Arrangement.Absolute.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, bottom = 20.dp)
            ) {
                Button(onClick = {
                    viewModel.swapRows()
                }) {
                    Box(modifier = Modifier) {
                        Icon(
                            painter = painterResource(id = R.drawable.change_values_icon),
                            contentDescription = "change_values"
                        )
                    }

                }
            }
            ConvertItem(
                secondRowState,
                contentDescription = "img ${secondRowState.symbol}"
            ) {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                    isFistRowOpen = false
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
            )

            Column {
                Row(modifier = Modifier.weight(1f)) {
                    ConvertButton(text = "1", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(1))
                    }
                    ConvertButton(text = "2", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(2))
                    }
                    ConvertButton(text = "3", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(3))
                    }
                }
                Row(modifier = Modifier.weight(1f)) {
                    ConvertButton(text = "4", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(4))
                    }
                    ConvertButton(text = "5", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(5))
                    }
                    ConvertButton(text = "6", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(6))
                    }
                }
                Row(modifier = Modifier.weight(1f)) {
                    ConvertButton(text = "7", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(7))
                    }
                    ConvertButton(text = "8", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(8))
                    }
                    ConvertButton(text = "9", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(9))
                    }
                }
                Row(modifier = Modifier.weight(1f)) {
                    ConvertButton(text = ".", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Decimal)
                    }
                    ConvertButton(text = "0", modifier = Modifier.weight(1f)) {
                        viewModel.readInput(ActionInput.Number(0))
                    }
                    ConvertButton(
                        icon = R.drawable.eraser_icon,
                        modifier = Modifier.weight(1f)
                    ) {
                        viewModel.readInput(ActionInput.Erase)
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = searchQuery) {
        fiatSearchListViewModel.search(searchQuery)
        cryptoSearchListViewModel.search(searchQuery)
    }
    LaunchedEffect(
        key1 = firstRowState.sum,
        key2 = firstRowState,
        key3 = secondRowState
    ) {
        viewModel.convert(firstRowState.sum.replace(",", ".").toDouble())
    }
}









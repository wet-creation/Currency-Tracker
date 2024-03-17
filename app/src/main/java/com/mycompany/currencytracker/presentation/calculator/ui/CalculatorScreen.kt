package com.mycompany.currencytracker.presentation.calculator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.mycompany.currencytracker.presentation.common.currency.ListScreen
import com.mycompany.currencytracker.presentation.common.currency.SearchPosition
import com.mycompany.currencytracker.presentation.common.currency.StateListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoListScreen
import com.mycompany.currencytracker.presentation.common.currency.crypto.CryptoSearchListViewModel
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatListScreen
import com.mycompany.currencytracker.presentation.common.currency.fiat.FiatSearchListViewModel
import com.mycompany.currencytracker.presentation.ui.theme.selectTextColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val viewModel = hiltViewModel<CalculatorViewModel>()
    val fiatSearchListViewModel = hiltViewModel<FiatSearchListViewModel>()
    val cryptoSearchListViewModel = hiltViewModel<CryptoSearchListViewModel>()
    val rowState1 = viewModel.calculatorState1.value
    val rowState2 = viewModel.calculatorState2.value
    val sheetScaffoldState = rememberModalBottomSheetState()
    var searchQuery by remember {
        mutableStateOf("")
    }
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetScaffoldState
    )
    val scope = rememberCoroutineScope()

    var isFistRowOpen = false



    BottomSheetScaffold(
        sheetContent = {
            ListScreen(
                searchPosition = SearchPosition.InBetween,
                stateListScreen = StateListScreen(searchQuery) {
                    searchQuery = it
                },
                fiatListScreen = {
                    FiatListScreen(
                        haveHeader = false,
                        viewModel = fiatSearchListViewModel,
                        itemContent = { currencyItem, _ ->
                            FiatListItem(currencyItem) {
                                if (isFistRowOpen) {
                                    viewModel.getRate(
                                        currencyItem.symbol,
                                        viewModel.calculatorState2.value.symbol
                                    )
                                    viewModel.getFiat(currencyItem.symbol, true)
                                }
                                else {
                                    viewModel.getRate(
                                        viewModel.calculatorState1.value.symbol,
                                        currencyItem.symbol
                                    )
                                    viewModel.getFiat(currencyItem.symbol, false)
                                }
                                scope.launch {
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                        })
                },
                cryptoListScreen = {
                    CryptoListScreen(
                        haveHeader = false,
                        viewModel = cryptoSearchListViewModel,
                        itemContent = { crypto ->
                            CryptoListItem(crypto = crypto) {
                                if (isFistRowOpen) {
                                    viewModel.getRate(
                                        crypto.symbol,
                                        viewModel.calculatorState2.value.symbol
                                    )
                                    viewModel.getCrypto(crypto.symbol, true)
                                }
                                else {
                                    viewModel.getRate(
                                        viewModel.calculatorState1.value.symbol,
                                        crypto.symbol
                                    )
                                    viewModel.getCrypto(crypto.symbol, false)
                                }
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            ConvertItem(
                rowState1,
                contentDescription = "img ${rowState1.symbol}"
            ) {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                    isFistRowOpen = true
                }
            }
            Button(onClick = {
                viewModel.swapRows()
            }) {
                Text(text = "SwapRows")
            }

            ConvertItem(
                rowState2,
                contentDescription = "img ${rowState2.symbol}"
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
                    ConvertButton(icon = R.drawable.eraser_icon, modifier = Modifier.weight(1f)) {
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
    LaunchedEffect(key1 = rowState1.sum) {

        viewModel.convert(rowState1.sum.replace(",",".").toDouble())
    }
}









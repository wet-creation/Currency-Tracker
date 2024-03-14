package com.mycompany.currencytracker.presentation.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Constants.image_url
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.Convert
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.domain.model.currency.fiat.Currency
import com.mycompany.currencytracker.domain.use_case.convert.ConvertUseCase
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoDetailsUseCase
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrencyDetailsUseCase
import com.mycompany.currencytracker.presentation.calculator.states.ActionInput
import com.mycompany.currencytracker.presentation.calculator.states.RowStateCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val convertor: ConvertUseCase,
    private val currencyDetailsUseCase: GetCurrencyDetailsUseCase,
    private val cryptoDetailsUseCase: GetCryptoDetailsUseCase,
    private val userSetting: StoreUserSetting
) : ViewModel() {

    private var _calculatorRowState1 = mutableStateOf(RowStateCalculator())
    val calculatorState1: State<RowStateCalculator> = _calculatorRowState1
    private var _calculatorRowState2 = mutableStateOf(RowStateCalculator())
    val calculatorState2: State<RowStateCalculator> = _calculatorRowState2
    private var convertResult = Convert()


    init {
        initStates()
    }

    fun convert(fromSymbol: String, toSymbol: String, sum: Double) {
        convertor(fromSymbol, toSymbol, sum).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    convertResult = result.data ?: Convert()
                    if (toSymbol == _calculatorRowState1.value.symbol)
                        _calculatorRowState1.value =
                            _calculatorRowState1.value.copy(sum = convertResult.response.toString())
                    else
                        _calculatorRowState2.value =
                            _calculatorRowState2.value.copy(sum = convertResult.response.toString())

                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initStates() {
        getFiat(userSetting.getCurrency())
        getCrypto(userSetting.getCrypto())
    }
    private fun getFiat(symbol: String) {
        var fiatDetails: Currency
        currencyDetailsUseCase(symbol).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    fiatDetails = result.data ?: Currency()
                    _calculatorRowState1.value = _calculatorRowState1.value.copy(
                        image = image_url + fiatDetails.symbol.lowercase() + ".png",
                        symbol = fiatDetails.symbol,
                        name = fiatDetails.name
                    )
                    debugLog("Fiat ${_calculatorRowState1.value}")
                    debugLog("Data store fiat ${userSetting.getCurrency()}")
                }

                is Resource.Error -> {
                    debugLog("error fiat${result.message!!}")
                }

                is Resource.Loading -> {
                    debugLog("loading fiat")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCrypto(symbol: String) {
        var cryptoDetails: CryptoDetails
        cryptoDetailsUseCase(symbol).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    cryptoDetails = result.data ?: CryptoDetails()
                    _calculatorRowState2.value = _calculatorRowState2.value.copy(
                        image = cryptoDetails.image,
                        symbol = cryptoDetails.symbol.uppercase(),
                        name = cryptoDetails.name
                    )
                    debugLog("Crypto ${_calculatorRowState2.value}")
                    debugLog("Data store crypto ${userSetting.getCrypto()}")

                }

                is Resource.Error -> {
                    debugLog("error crypto${result.message!!}")
                }

                is Resource.Loading -> {
                    debugLog("loading crypto ")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun readInput(input: ActionInput) {
        when (input) {
            is ActionInput.Number -> {
                writeNumber(input.number)
            }

            is ActionInput.Erase -> {
                erase()
            }

            is ActionInput.Decimal -> {
                writeDecimal()
            }
        }
    }

    private fun writeDecimal() {
        if (!_calculatorRowState1.value.sum.contains("."))
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = _calculatorRowState1.value.sum + ".")
    }

    private fun erase() {
        if (_calculatorRowState1.value.sum != "0") {
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = "" + _calculatorRowState1.value.sum.dropLast(1))
            if (_calculatorRowState1.value.sum.isEmpty()) {
                _calculatorRowState1.value = _calculatorRowState1.value.copy(sum = "0")
            }
        }

    }

    private fun writeNumber(number: Int) {
        if (_calculatorRowState1.value.sum == "0")
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = "" + number.toString())
        else
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = _calculatorRowState1.value.sum + number.toString())
    }

}

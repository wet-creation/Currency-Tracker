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
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
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
    private var rate: Double = 0.0


    init {
        initStates()
    }

    fun convert(sum: Double) {
        val result = rate * sum
        _calculatorRowState2.value =
            _calculatorRowState2.value.copy(sum = String.format("%.4f", result).replace(",", "."))
    }

    private fun initStates() {
        getFiat(userSetting.getCurrency())
        getCrypto(userSetting.getCrypto())
        getRate(userSetting.getCurrency(), userSetting.getCrypto())

    }

    fun getRate(fromSymbol: String, toSymbol: String) {
        convertor(fromSymbol, toSymbol).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    convertResult = result.data ?: Convert()
                    rate = convertResult.rate
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun getFiat(symbol: String, isFirstRow: Boolean = true) {
        var fiatDetails: FiatDetails
        currencyDetailsUseCase(symbol).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    fiatDetails = result.data ?: FiatDetails()
                    if (isFirstRow)
                        _calculatorRowState1.value = _calculatorRowState1.value.copy(
                            image = image_url + fiatDetails.symbol.lowercase() + ".png",
                            symbol = fiatDetails.symbol,
                            name = fiatDetails.name
                        )
                    else
                        _calculatorRowState2.value = _calculatorRowState2.value.copy(
                            image = image_url + fiatDetails.symbol.lowercase() + ".png",
                            symbol = fiatDetails.symbol,
                            name = fiatDetails.name
                        )
                    convert(_calculatorRowState1.value.sum.toDouble())
                }

                is Resource.Error -> {
                }

                is Resource.Loading -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCrypto(symbol: String, isFirstRow: Boolean = false) {
        var cryptoDetails: CryptoDetails
        cryptoDetailsUseCase(symbol).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    cryptoDetails = result.data ?: CryptoDetails()
                    if (isFirstRow)
                        _calculatorRowState1.value = _calculatorRowState1.value.copy(
                            image = cryptoDetails.image,
                            symbol = cryptoDetails.symbol.uppercase(),
                            name = cryptoDetails.name
                        )
                    else
                        _calculatorRowState2.value = _calculatorRowState2.value.copy(
                            image = cryptoDetails.image,
                            symbol = cryptoDetails.symbol.uppercase(),
                            name = cryptoDetails.name
                        )
                    convert(_calculatorRowState1.value.sum.toDouble())

                }

                is Resource.Error -> {
                }

                is Resource.Loading -> {
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
        debugLog("input ${_calculatorRowState1.value.symbol} ${_calculatorRowState1.value.sum}")

    }

    private fun writeDecimal() {
        if (!_calculatorRowState1.value.sum.contains("."))
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = _calculatorRowState1.value.sum + ".")
    }

    private fun erase() {
        if (_calculatorRowState1.value.sum == "0") return

        _calculatorRowState1.value =
            _calculatorRowState1.value.copy(sum = _calculatorRowState1.value.sum.dropLast(1))
        if (_calculatorRowState1.value.sum.isEmpty()) {
            _calculatorRowState1.value = _calculatorRowState1.value.copy(sum = "0")
        }


    }

    private fun writeNumber(number: Int) {
        if (countDigitsAfterDecimalPoint(_calculatorRowState1.value.sum) > 3)
            return

        if (_calculatorRowState1.value.sum == "0")
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = number.toString())
        else
            _calculatorRowState1.value =
                _calculatorRowState1.value.copy(sum = _calculatorRowState1.value.sum + number.toString())
    }

    fun swapRows() {
        val tmp = _calculatorRowState1.value
        if (_calculatorRowState2.value.sum == "0.0000") _calculatorRowState1.value =
            _calculatorRowState2.value.copy(sum = "0")
        else _calculatorRowState1.value = _calculatorRowState2.value
        _calculatorRowState2.value = tmp
        rate = 1 / rate

    }

    private fun countDigitsAfterDecimalPoint(number: String): Int {
        val parts = number.split(".")
        if (parts.size == 1) return 0
        return parts[1].length
    }

}

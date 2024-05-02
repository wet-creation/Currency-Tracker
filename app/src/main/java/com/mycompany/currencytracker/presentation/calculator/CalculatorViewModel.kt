package com.mycompany.currencytracker.presentation.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Constants.IMAGE_URL
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoDetailsUseCase
import com.mycompany.currencytracker.domain.use_case.currency.GetFiatDetailsUseCase
import com.mycompany.currencytracker.presentation.calculator.states.ActionInput
import com.mycompany.currencytracker.presentation.calculator.states.CalculatorScreenState
import com.mycompany.currencytracker.presentation.calculator.states.RowStateCalculator
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val fiatDetailsUseCase: GetFiatDetailsUseCase,
    private val cryptoDetailsUseCase: GetCryptoDetailsUseCase,
    private val userSetting: StoreUserSetting,
) : ViewModel() {


    private var _screenState = mutableStateOf(CalculatorScreenState())
    val screenState: State<CalculatorScreenState> = _screenState


    init {
        initStates()
    }

    fun initStates() {
        val flowFiat = fiatDetailsUseCase(userSetting.getFiat())
        val flowCrypto = cryptoDetailsUseCase(userSetting.getCrypto())

        viewModelScope.launch {
            combine(flowFiat, flowCrypto) { fiat, crypto ->
                listOf(fiat, crypto)
            }.collect { list ->
                val updatedState = list.fold(_screenState.value) { acc, resource ->
                    when (resource) {
                        is Resource.Error -> acc.copy(error = resource.asErrorUiText())
                        is Resource.Loading -> acc.copy(isLoading = true)
                        is Resource.Success -> updateRowState(acc, resource.data)
                    }
                }
                _screenState.value = updatedState.calculateExchangeRate()
            }
        }
    }

    private fun updateRowState(state: CalculatorScreenState, data: Any): CalculatorScreenState {
        return when (data) {
            is CryptoDetails, is CryptoGeneralInfo -> state.copy(
                secondRowState = convertToRowState(data)
            )
            is FiatDetails -> state.copy(
                firstRowState = convertToRowState(data),
                isLoading = false
            )
            else -> state
        }
    }

    private fun CalculatorScreenState.calculateExchangeRate(): CalculatorScreenState {
        return this.copy(rate = this.firstRowState.rate / this.secondRowState.rate)
    }

    private fun convertToRowState(data: Any): RowStateCalculator {
        return when (data) {
            is CryptoDetails -> RowStateCalculator(
                image = data.image,
                symbol = data.symbol.uppercase(),
                name = data.name,
                rate = data.rate
            )
            is CryptoGeneralInfo -> RowStateCalculator(
                image = data.image,
                symbol = data.symbol.uppercase(),
                name = data.name,
                rate = data.rate
            )
            is FiatDetails -> RowStateCalculator(
                image = "$IMAGE_URL/${data.symbol.lowercase()}.png",
                symbol = data.symbol,
                name = data.name,
                rate = data.rate
            )
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    fun setRowState(data: Any, isSecondRow: Boolean = false) {
        viewModelScope.launch(Dispatchers.Default) {
            val updatedState = if (isSecondRow) {
                _screenState.value.copy(secondRowState = convertToRowState(data))
            } else {
                _screenState.value.copy(firstRowState = convertToRowState(data))
            }.calculateExchangeRate()

            _screenState.value = updatedState
        }
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

    fun convert(sum: Double) {
        val result = _screenState.value.rate * sum
        _screenState.value =
            _screenState.value.copy(
                secondRowState = _screenState.value.secondRowState.copy(
                    sum = String.format("%.4f", result).replace(",", ".")
                )
            )
    }

    private fun writeDecimal() {
        if (!_screenState.value.firstRowState.sum.contains("."))
            _screenState.value =
                _screenState.value.copy(
                    firstRowState = _screenState.value.firstRowState.copy(
                        sum = _screenState.value.firstRowState.sum + "."
                    )
                )
    }

    private fun erase() {
        if (_screenState.value.firstRowState.sum == "0") return

        _screenState.value =
            _screenState.value.copy(
                firstRowState = _screenState.value.firstRowState.copy
                    (sum = _screenState.value.firstRowState.sum.dropLast(1))
            )
        if (_screenState.value.firstRowState.sum.isEmpty()) {
            _screenState.value =
                _screenState.value.copy(
                    firstRowState = _screenState.value.firstRowState.copy
                        (sum = "0")
                )
        }
    }

    private fun writeNumber(number: Int) {
        if (countDigitsAfterDecimalPoint(_screenState.value.firstRowState.sum) > 3)
            return
        if (_screenState.value.firstRowState.sum == "0")
            _screenState.value = _screenState.value.copy(
                firstRowState = _screenState.value.firstRowState.copy(sum = number.toString())
            )
        else
            _screenState.value = _screenState.value.copy(
                firstRowState = _screenState.value.firstRowState.copy(sum = _screenState.value.firstRowState.sum + number.toString())
            )
    }

    fun swapRows() {
        val tmp = _screenState.value.firstRowState
        if (_screenState.value.secondRowState.sum == "0.0000") _screenState.value =
            _screenState.value.copy(
                firstRowState = _screenState.value.secondRowState.copy(sum = "0"),
                secondRowState = tmp,
                rate = 1 / _screenState.value.rate
            )
        else _screenState.value =
            _screenState.value.copy(
                firstRowState = _screenState.value.secondRowState,
                secondRowState = tmp,
                rate = 1 / _screenState.value.rate
            )
    }

    private fun countDigitsAfterDecimalPoint(number: String): Int {
        val parts = number.split(".")
        if (parts.size == 1) return 0
        return parts[1].length
    }

    fun dismissDialog() {
        _screenState.value = CalculatorScreenState(isLoading = false)
    }
}

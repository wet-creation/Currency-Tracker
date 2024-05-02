package com.mycompany.currencytracker.presentation.notification_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.toCryptoGeneralInfo
import com.mycompany.currencytracker.domain.model.user.notification.UserNotification
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoDetailsUseCase
import com.mycompany.currencytracker.domain.use_case.user.SaveNotificationUseCase
import com.mycompany.currencytracker.presentation.calculator.states.ActionInput
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.currency.crypto.calculateDecimalPlaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewMode @Inject constructor(
    private val getCryptoDetailsUseCase: GetCryptoDetailsUseCase,
    private val saveNotificationUseCase: SaveNotificationUseCase,
    savedStateHandle: SavedStateHandle,
    private val userSetting: StoreUserSetting
) : ViewModel() {
    private var _screenState = mutableStateOf(
        NotificationScreenState(),
    )
    var screenState: State<NotificationScreenState> = _screenState
    private var _navigate = mutableStateOf(false)
    val navigate: State<Boolean> = _navigate

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            initStates(coinId)
        }
    }

    private fun initStates(symbol: String) {
        _screenState.value = _screenState.value.copy(symbolForCrypto = userSetting.getCrypto())
        _screenState.value = _screenState.value.copy(symbolForFiat = userSetting.getFiat())
        val flowBaseFiat = getCryptoDetailsUseCase(symbol, _screenState.value.symbolForFiat)
        val flowBaseCrypto = getCryptoDetailsUseCase(symbol, _screenState.value.symbolForCrypto)
        viewModelScope.launch {
            combine(flowBaseFiat, flowBaseCrypto) { fiat, crypto ->
                mapOf(
                    _screenState.value.symbolForFiat to fiat,
                    _screenState.value.symbolForCrypto to crypto
                )
            }.collect { map ->
                map.forEach { (key, value) ->
                    when (value) {
                        is Resource.Error -> _screenState.value =
                            _screenState.value.copy(error = value.asErrorUiText())

                        is Resource.Loading -> _screenState.value =
                            _screenState.value.copy(isLoading = true)

                        is Resource.Success -> {
                            if (key == _screenState.value.symbolForFiat) {
                                _screenState.value =
                                    _screenState.value.copy(
                                        currentPriceForFiat = calculateDecimalPlaces(value.data.rate),
                                        crypto = value.data.toCryptoGeneralInfo(),
                                        writtenPrice = calculateDecimalPlaces(value.data.rate).replace(
                                            ",",
                                            "."
                                        ),
                                        isLoading = false
                                    )
                            } else
                                _screenState.value =
                                    _screenState.value.copy(
                                        currentPriceForCrypto = calculateDecimalPlaces(
                                            value.data.rate
                                        )
                                    )
                        }
                    }
                }
            }
        }
    }

    fun changeWidth(size: Dp) {
        _screenState.value = _screenState.value.copy(textWidth = size)
        debugLog(_screenState.value.textWidth.value.toString())
    }

    fun saveNotification() {
        viewModelScope.launch {
            val selectedCurrency =
                if (_screenState.value.fiatSelected) _screenState.value.symbolForFiat
                else _screenState.value.symbolForCrypto
            val notification = UserNotification(
                ",",
                userSetting.getUser().id,
                _screenState.value.crypto.symbol,
                _screenState.value.writtenPrice.toDouble(),
                isMoreThanTarget = false,
                isConstantly = false,
                image = "", marketCapRank = 0,
                name = ""
            )
            saveNotificationUseCase(notification, selectedCurrency).collect {
                when (it) {
                    is Resource.Error -> _screenState.value =
                        _screenState.value.copy(error = it.asErrorUiText())

                    is Resource.Loading -> _screenState.value =
                        _screenState.value.copy(isLoading = true)

                    is Resource.Success -> _navigate.value = true
                }
            }
        }
    }


    fun selectBase(isFiat: Boolean) {
        val selectedPrice =
            if (isFiat) _screenState.value.currentPriceForFiat
            else _screenState.value.currentPriceForCrypto
        _screenState.value =
            _screenState.value.copy(fiatSelected = isFiat, writtenPrice = selectedPrice)
    }


    fun readInput(input: ActionInput) {
        resizeText()
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

    private fun resizeText() {
//        val width = _screenState.value.textWidth
//        var textSize = _screenState.value.textSize
//
//        var textSize =
//            _screenState.value.textSize * (_screenState.value.writtenPrice.length.dp / _screenState.value.textWidth)
//        if (textSize > 48.sp)
//            textSize = 48.sp
//        if (textSize < 24.sp)
//            textSize = 24.sp
//        _screenState.value = _screenState.value.copy(textSize = textSize)

    }

    private fun writeDecimal() {
        if (!_screenState.value.writtenPrice.contains("."))
            _screenState.value =
                _screenState.value.copy(
                    writtenPrice = _screenState.value.writtenPrice + "."
                )
    }

    private fun erase() {
        if (_screenState.value.writtenPrice == "0") return

        _screenState.value =
            _screenState.value.copy(
                writtenPrice = _screenState.value.writtenPrice.dropLast(1)
            )
        if (_screenState.value.writtenPrice.isEmpty()) {
            _screenState.value =
                _screenState.value.copy(
                    writtenPrice = "0"
                )
        }
    }

    private fun writeNumber(number: Int) {
        if (countDigitsAfterDecimalPoint(_screenState.value.writtenPrice) > 3)
            return
        if (_screenState.value.writtenPrice == "0")
            _screenState.value = _screenState.value.copy(
                writtenPrice = number.toString()
            )
        else
            _screenState.value = _screenState.value.copy(
                writtenPrice = _screenState.value.writtenPrice + number.toString()
            )
    }

    private fun countDigitsAfterDecimalPoint(number: String): Int {
        val parts = number.split(".")
        if (parts.size == 1) return 0
        return parts[1].length
    }
}
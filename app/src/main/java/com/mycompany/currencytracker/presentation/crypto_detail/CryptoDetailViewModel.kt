package com.mycompany.currencytracker.presentation.crypto_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoDetails
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val getCryptoDetailsUseCase: GetCryptoDetailsUseCase,
    savedStateHandle: SavedStateHandle,
    private val userSettings: StoreUserSetting
) : ViewModel() {
    private val _state = mutableStateOf(CryptoDetailState())
    val state: State<CryptoDetailState> = _state

    private var _fiatRate = mutableStateOf<CryptoDetails?>(null)
    val fiatRate : State<CryptoDetails?> = _fiatRate

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinSym: String) {
        getCryptoDetailsUseCase(coinSym, userSettings.getCrypto()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CryptoDetailState(crypto = result.data)
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {
                    _state.value = CryptoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

        getCryptoDetailsUseCase(coinSym, userSettings.getCurrency()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _fiatRate.value = result.data
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {
                    _state.value = CryptoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
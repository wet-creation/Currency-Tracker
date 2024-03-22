package com.mycompany.currencytracker.presentation.currency_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.use_case.currency.GetFiatAdditionalInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailViewModel @Inject constructor(
    private val getCurrencyAdditionalInfo: GetFiatAdditionalInfoUseCase,
    savedStateHandle: SavedStateHandle,
    private val userSettings: StoreUserSetting
) : ViewModel() {
    private val _state = mutableStateOf(CurrencyDetailState())
    val state: State<CurrencyDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_CURRENCY_ID)?.let {currencyId ->
            getCurrency(currencyId)
        }
    }

    private fun getCurrency(currencySym: String) {
        getCurrencyAdditionalInfo(currencySym, userSettings.getCurrency()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CurrencyDetailState(currency = result.data)
                }
                is Resource.Error -> {
                    _state.value = CurrencyDetailState(
                        error = result.message ?: "an unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CurrencyDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
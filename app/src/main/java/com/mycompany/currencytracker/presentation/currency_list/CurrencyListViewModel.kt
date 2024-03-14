package com.mycompany.currencytracker.presentation.currency_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrenciesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val getCurrenciesListUseCase: GetCurrenciesListUseCase,
    private val userSettings: StoreUserSetting
) : ViewModel() {
    private val _state = mutableStateOf(CurrencyListState())
    val state: State<CurrencyListState> = _state
    init {
        getCurrencies()
    }

    fun getCurrencies(){
        getCurrenciesListUseCase(userSettings.getCurrency()).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CurrencyListState(currencies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = CurrencyListState(
                        error = result.message ?: "an unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CurrencyListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
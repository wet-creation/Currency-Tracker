package com.mycompany.currencytracker.presentation.setting_screen.currencySelectScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrenciesListUseCase
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencySelectListViewModel @Inject constructor(
    private val getCurrenciesListUseCase: GetCurrenciesListUseCase

) : ViewModel() {
    private val _state = mutableStateOf(CurrencyListState())
    val state: State<CurrencyListState> = _state

    init {
        getCurrencies()
    }

    private fun getCurrencies(){
        getCurrenciesListUseCase().onEach { result ->
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
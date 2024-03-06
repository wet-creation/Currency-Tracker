package com.mycompany.currencytracker.presentation.currency_list

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrenciesListUseCase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
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

    private fun getCurrencies(){
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
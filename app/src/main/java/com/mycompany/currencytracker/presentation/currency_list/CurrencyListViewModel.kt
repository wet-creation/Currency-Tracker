package com.mycompany.currencytracker.presentation.currency_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrenciesListUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val getCurrenciesListUseCase: GetCurrenciesListUseCase,
    private val userSettings: StoreUserSetting
) : ViewModel(), IListViewModel<FiatDetails> {
    private val _state = mutableStateOf(CurrencyListState())
    override val state: State<CurrencyListState> = _state

    init {
        getItems()
    }

    override fun getItems() {
        getCurrenciesListUseCase(userSettings.getCurrency()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CurrencyListState(currencies = result.data)
                }

                is Resource.Error -> {
                    val msg = result.asErrorUiText()
                    _state.value = CurrencyListState(error = msg)
                }

                is Resource.Loading -> {
                    _state.value = CurrencyListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
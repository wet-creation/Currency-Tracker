package com.mycompany.currencytracker.presentation.common.currency.fiat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.currency.fiat.FiatDetails
import com.mycompany.currencytracker.domain.use_case.currency.GetCurrenciesListUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.currency_list.CurrencyListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FiatSearchListViewModel @Inject constructor(
    private val getCurrenciesListUseCase: GetCurrenciesListUseCase
) : ViewModel(), IListViewModel<FiatDetails> {

    private val _state = mutableStateOf(CurrencyListState())
    override val state: State<CurrencyListState> = _state

    private val _searchResult = mutableStateOf<List<FiatDetails>>(emptyList())
    val searchResult: State<List<FiatDetails>> = _searchResult

    init {
        getItems()
    }
     override fun getItems(){
        getCurrenciesListUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val currencies = result.data
                    _state.value = CurrencyListState(items = currencies)
                    _searchResult.value = currencies
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

    fun search(query: String) {
        val filteredList = _state.value.items.filter { currency ->
            currency.name.contains(query, ignoreCase = true) || currency.symbol.contains(query, ignoreCase = true)
        }
        _searchResult.value = filteredList
    }
}

package com.mycompany.currencytracker.presentation.common.currency.crypto

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.domain.use_case.crypto.GetTop100RateUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.crypto_list.CryptoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * ViewModel responsible for managing the state and data related to the cryptocurrency search list screen.
 * Extends [ViewModel] and implements [IListViewModel] for handling the list functionality.
 *
 * @property getTop100RateUseCase The use case responsible for fetching the top 100 cryptocurrency rates.
 */
@HiltViewModel
class CryptoSearchListViewModel @Inject constructor(
    private val getTop100RateUseCase: GetTop100RateUseCase,
    val userSetting: StoreUserSetting
) : ViewModel(), IListViewModel<CryptoGeneralInfo> {

    /** Mutable state for holding the current state of the screen. */
    private val _state = mutableStateOf(CryptoListState())
    /** The current state of the screen exposed as [State]. */
    override val state: State<CryptoListState> = _state
    /** Mutable state for holding the search result list of cryptocurrencies. */
    private val _searchResult = mutableStateOf<List<CryptoGeneralInfo>>(emptyList())
    /** The search result list of cryptocurrencies exposed as [State]. */
    val searchResult: State<List<CryptoGeneralInfo>> = _searchResult

    /**
     * Initializes the ViewModel by fetching the initial list of cryptocurrencies.
     */
    init {
        getItems()
    }

    /**
     * Fetches the list of cryptocurrencies using the [getTop100RateUseCase].
     */
    override fun getItems(vararg args: Any) {
        getTop100RateUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val cryptos = result.data
                    _state.value = CryptoListState(items = cryptos)
                    _searchResult.value = cryptos
                }
                is Resource.Error -> {
                    val msg = result.asErrorUiText()
                    _state.value = CryptoListState(error = msg)

                }
                is Resource.Loading -> {
                    _state.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Performs a search operation on the list of cryptocurrencies based on the provided [query].
     * Updates the [_searchResult] with the filtered list.
     *
     * @param query The search query to filter the list of cryptocurrencies.
     */
    fun search(query: String) {
        val filteredList = _state.value.items.filter { crypto ->
            crypto.name.contains(query, ignoreCase = true) || crypto.symbol.contains(query, ignoreCase = true)
        }
        _searchResult.value = filteredList
    }
}
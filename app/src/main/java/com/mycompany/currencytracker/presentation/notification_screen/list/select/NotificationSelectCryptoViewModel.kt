package com.mycompany.currencytracker.presentation.notification_screen.list.select

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.notification.CryptoSelectNotification
import com.mycompany.currencytracker.domain.model.user.notification.toCryptoSelectNotification
import com.mycompany.currencytracker.domain.use_case.crypto.GetTop100RateUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationSelectCryptoViewModel @Inject constructor(
    val getCryptos: GetTop100RateUseCase
) : ViewModel(), IListViewModel<CryptoSelectNotification> {
    private var _state = mutableStateOf(NotificationSelectCryptoScreenState())
    override val state: State<NotificationSelectCryptoScreenState> = _state
    private val _searchResult = mutableStateOf<List<CryptoSelectNotification>>(emptyList())
    val searchResult: State<List<CryptoSelectNotification>> = _searchResult

    init {
        getItems()
    }

    override fun getItems(vararg args: Any) {
        getCryptos().onEach { result ->
            when (result) {
                is Resource.Error -> _state.value =
                    NotificationSelectCryptoScreenState(error = result.asErrorUiText())

                is Resource.Loading -> _state.value =
                    NotificationSelectCryptoScreenState(isLoading = true)

                is Resource.Success -> {
                    _state.value =
                        NotificationSelectCryptoScreenState(items = result.data.map {
                            it.toCryptoSelectNotification()
                        })
                    _searchResult.value = _state.value.items
                }
            }
        }.launchIn(viewModelScope)
    }

    fun search(query: String) {
        val filteredList = _state.value.items.filter { crypto ->
            crypto.name.contains(query, ignoreCase = true) || crypto.symbol.contains(
                query,
                ignoreCase = true
            )
        }
        _searchResult.value = filteredList
    }


}
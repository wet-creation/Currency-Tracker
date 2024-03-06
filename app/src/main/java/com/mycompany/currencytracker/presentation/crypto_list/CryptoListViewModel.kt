package com.mycompany.currencytracker.presentation.crypto_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.use_case.crypto.GetTop100RateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val getTop100RateUseCase: GetTop100RateUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CryptoListState())
    val state: State<CryptoListState> = _state

    init {
        getCryptos()
    }

    private fun getCryptos(){
        getTop100RateUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CryptoListState(cryptos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = CryptoListState(
                        error = result.message ?: "an unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CryptoListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
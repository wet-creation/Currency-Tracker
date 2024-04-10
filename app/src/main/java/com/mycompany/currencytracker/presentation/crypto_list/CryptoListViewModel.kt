package com.mycompany.currencytracker.presentation.crypto_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.currency.crypto.CryptoGeneralInfo
import com.mycompany.currencytracker.domain.use_case.crypto.GetTop100RateUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListState
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class  CryptoListViewModel @Inject constructor(
    private val getTop100RateUseCase: GetTop100RateUseCase,
    private val userSettings: StoreUserSetting
) : ViewModel(), IListViewModel<CryptoGeneralInfo> {

    private val _state = mutableStateOf(CryptoListState())
    override val state: State<IListState<CryptoGeneralInfo>> = _state


    init {
        getItems()
    }

    override fun getItems(){
        getTop100RateUseCase(userSettings.getCrypto()).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CryptoListState(items = result.data)
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
}
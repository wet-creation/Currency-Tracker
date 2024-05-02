package com.mycompany.currencytracker.presentation.crypto_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.user.FollowedCrypto
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoDetailsUseCase
import com.mycompany.currencytracker.domain.use_case.crypto.GetCryptoGraphInfoUseCase
import com.mycompany.currencytracker.domain.use_case.user.DeleteCryptoFromFavoriteUseCase
import com.mycompany.currencytracker.domain.use_case.user.FollowedCryptoUseCase
import com.mycompany.currencytracker.domain.use_case.user.GetCryptoFollowStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val getCryptoDetailsUseCase: GetCryptoDetailsUseCase,
    private val getCryptoGraphInfo: GetCryptoGraphInfoUseCase,
    private val getCryptoFollowStatusUseCase: GetCryptoFollowStatusUseCase,
    private val followedCryptoUseCase: FollowedCryptoUseCase,
    private val deleteCryptoFromFavoriteUseCase: DeleteCryptoFromFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userSettings: StoreUserSetting
) : ViewModel() {
    private val _state = mutableStateOf(CryptoDetailState())
    val state: State<CryptoDetailState> = _state

    private val _graphInfo = mutableStateOf<Map<Point, Long>>(mutableMapOf())
    val graphInfo: State<Map<Point, Long>> = _graphInfo

    private val _secondRate = mutableDoubleStateOf(0.0)
    val secondRate: State<Double> = _secondRate

    private val _followStatus = mutableStateOf(false)
    val followStatus: State<Boolean> = _followStatus

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinSym: String) {
        getCryptoDetailsUseCase(coinSym, userSettings.getSelectViewCurrency()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CryptoDetailState(cryptoSelected = result.data)
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {
                    _state.value = CryptoDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

        getCryptoFollowStatusUseCase(coinSym, userSettings.getUser().id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _followStatus.value = true
                }
                is Resource.Error -> {
                    _followStatus.value = false
                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)

        getCryptoGraphInfo(
            userSettings.getChartTime(),
            coinSym,
            userSettings.getSelectViewCurrency()
        ).onEach { result ->
            if (result is Resource.Success) {
                _graphInfo.value = result.data
            }
        }.launchIn(viewModelScope)

        getCryptoDetailsUseCase(coinSym, userSettings.getSecondViewCurrency()).onEach { result ->
            if (result is Resource.Success) {
                _secondRate.doubleValue = result.data.rate

            }


        }.launchIn(viewModelScope)
    }

    private fun updateGraphInfo(coinSym: String) {
        getCryptoGraphInfo(
            userSettings.getChartTime(),
            coinSym,
            userSettings.getSelectViewCurrency()
        ).onEach { result ->
            if (result is Resource.Success) {
                _graphInfo.value = result.data
            }
        }.launchIn(viewModelScope)
    }
    fun addCryptoToFollowList(coinSym: String){
        val coin = FollowedCrypto(userSettings.getUser().id, 0, symbol = coinSym, null, null, null, null,null,0.0)

        followedCryptoUseCase(coin).onEach {
            when (it) {
                is Resource.Success -> {
                    _followStatus.value = true
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }
    fun removeCryptoFromFavoriteList(coinSym: String){
        deleteCryptoFromFavoriteUseCase(userSettings.getUser().id, coinSym).onEach {
            when (it) {
                is Resource.Success -> {
                    _followStatus.value = false
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }
    fun changeChartTime(newTime: String) {
        viewModelScope.launch {
            userSettings.saveChartTime(newTime)
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                updateGraphInfo(coinId)
            }
        }
    }

    fun refreshScreen(){
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

}
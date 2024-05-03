package com.mycompany.currencytracker.presentation.fiat_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.mycompany.currencytracker.common.Constants.PARAM_CURRENCY_ID
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.user.followed.FollowedFiat
import com.mycompany.currencytracker.domain.use_case.currency.GetFiatAdditionalInfoUseCase
import com.mycompany.currencytracker.domain.use_case.currency.GetFiatGraphInfoUseCase
import com.mycompany.currencytracker.domain.use_case.user.DeleteFiatFromFavoriteUseCase
import com.mycompany.currencytracker.domain.use_case.user.FollowedFiatUseCase
import com.mycompany.currencytracker.domain.use_case.user.GetFiatFollowStatusUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiatDetailViewModel @Inject constructor(
    private val getFiatAdditionalInfo: GetFiatAdditionalInfoUseCase,
    private val getFiatGraphInfo: GetFiatGraphInfoUseCase,
    private val getFiatFollowStatusUseCase: GetFiatFollowStatusUseCase,
    private val followedFiatUseCase: FollowedFiatUseCase,
    private val deleteFiatFromFavoriteUseCase: DeleteFiatFromFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val userSettings: StoreUserSetting
) : ViewModel() {
    private val _state = mutableStateOf(FiatDetailState())
    val state: State<FiatDetailState> = _state

    private val _graphInfo = mutableStateOf<Map<Point, Long>>(mutableMapOf())
    val graphInfo: State<Map<Point, Long>> = _graphInfo

    private val _followStatus = mutableStateOf(false)
    val followStatus: State<Boolean> = _followStatus

    init {
        savedStateHandle.get<String>(PARAM_CURRENCY_ID)?.let { currencyId ->
            getCurrency(currencyId)
        }
    }

    private fun getCurrency(currencySym: String) {
        viewModelScope.launch {
            getFiatAdditionalInfo(currencySym, userSettings.getFiat()).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(currency = result.data)
                    }

                    is Resource.Error -> {
                        _state.value = FiatDetailState(error = result.asErrorUiText())
                    }

                    is Resource.Loading -> {
                        _state.value = FiatDetailState(isLoading = true)
                    }
                }
            }

            getFiatFollowStatusUseCase(currencySym, userSettings.getUser().id).collect { result ->
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
            }

            updateGraphInfo(userSettings.getChartTime(), currencySym)

        }

    }

    private suspend fun updateGraphInfo(chartTime: String, currencySym: String) {
        getFiatGraphInfo(chartTime, currencySym, userSettings.getFiat()).collect {
            when (it) {
                is Resource.Success -> {
                    _graphInfo.value = it.data
                    _state.value = _state.value.copy(isLoading = false)
                }

                is Resource.Error -> {
                    _state.value = FiatDetailState(error = it.asErrorUiText())
                }

                is Resource.Loading -> {

                }
            }
        }
    }

    fun addFiatToFavoriteList(fiatSym: String) {
        val fiat =
            FollowedFiat(userSettings.getUser().id, 0, symbol = fiatSym, null, null, null, 0.0)

        followedFiatUseCase(fiat).onEach {
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

    fun removeFiatFromFavoriteList(fiatSym: String) {
        deleteFiatFromFavoriteUseCase(userSettings.getUser().id, fiatSym).onEach {
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
            savedStateHandle.get<String>(PARAM_CURRENCY_ID)?.let { currencyId ->
                updateGraphInfo(userSettings.getChartTime(), currencyId)
            }
        }
    }

    fun refreshScreen() {
        savedStateHandle.get<String>(PARAM_CURRENCY_ID)?.let { coinId ->
            getCurrency(coinId)
        }
    }
}
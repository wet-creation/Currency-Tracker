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
        savedStateHandle.get<String>(PARAM_CURRENCY_ID)?.let {currencyId ->
            getCurrency(currencyId)
        }
    }

    private fun getCurrency(currencySym: String) {
        getFiatAdditionalInfo(currencySym, userSettings.getFiat()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FiatDetailState(currency = result.data)
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                    _state.value = FiatDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)

        getFiatFollowStatusUseCase(currencySym, userSettings.getUser().id).onEach { result ->
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

        updateGraphInfo(userSettings.getChartTime(), currencySym)
    }
    private fun updateGraphInfo(chartTime: String, currencySym: String) {
        getFiatGraphInfo(chartTime, currencySym, userSettings.getFiat()).onEach {
            when (it) {
                is Resource.Success -> {
                    _graphInfo.value = it.data
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }
    fun addFiatToFavoriteList(fiatSym: String){
        val fiat = FollowedFiat(userSettings.getUser().id, 0, symbol = fiatSym, null, null, null, 0.0)

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
    fun removeFiatFromFavoriteList(fiatSym: String){
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
            savedStateHandle.get<String>(PARAM_CURRENCY_ID)?.let {currencyId ->
                updateGraphInfo(userSettings.getChartTime(), currencyId)
            }
        }
    }
}
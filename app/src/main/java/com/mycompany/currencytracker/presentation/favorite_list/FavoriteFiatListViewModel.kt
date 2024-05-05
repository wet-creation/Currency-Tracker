package com.mycompany.currencytracker.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.user.followed.FollowedFiat
import com.mycompany.currencytracker.domain.use_case.user.DeleteFiatFromFavoriteUseCase
import com.mycompany.currencytracker.domain.use_case.user.GetFavoriteFiatListUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.favorite_list.ui.states.FiatFollowedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFiatListViewModel @Inject constructor(
    private val getFavoriteFiatUseCase: GetFavoriteFiatListUseCase,
    private val deleteFiatFromFavoriteUseCase: DeleteFiatFromFavoriteUseCase,
    private val userSetting: StoreUserSetting
) : ViewModel(), IListViewModel<FollowedFiat> {

    private val _state = mutableStateOf(FiatFollowedState())
    override val state: State<FiatFollowedState> = _state


    init {
        getItems()
    }

    override fun getItems(vararg args: Any) {
        viewModelScope.launch {
            getFavoriteFiatUseCase(
                userId = userSetting.getUser().id,
                userSetting.getFiat()
            ).collect {
                when (it) {
                    is Resource.Error -> {
                        _state.value = FiatFollowedState(error = it.asErrorUiText())
                    }

                    is Resource.Loading -> {
                        _state.value = FiatFollowedState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _state.value = FiatFollowedState(items = it.data)
                    }
                }
            }
        }
    }

    fun delete(followedFiat: FollowedFiat) {
        deleteFiatFromFavoriteUseCase(userSetting.getUser().id, followedFiat.symbol).onEach {
            if (it is Resource.Error) {
                _state.value = FiatFollowedState(error = (it.asErrorUiText()))
            }
        }.launchIn(viewModelScope)
        val list = _state.value.items.toMutableList()
        list.remove(followedFiat)
        _state.value = _state.value.copy(items = list)
    }

}
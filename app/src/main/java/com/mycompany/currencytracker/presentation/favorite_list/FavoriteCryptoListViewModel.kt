package com.mycompany.currencytracker.presentation.favorite_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.user.followed.FollowedCrypto
import com.mycompany.currencytracker.domain.use_case.user.DeleteCryptoFromFavoriteUseCase
import com.mycompany.currencytracker.domain.use_case.user.GetFavoriteCryptoListUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import com.mycompany.currencytracker.presentation.favorite_list.ui.states.CryptoFollowedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteCryptoListViewModel @Inject constructor(
    private val getFavoriteCryptoUseCase: GetFavoriteCryptoListUseCase,
    private val deleteCryptoFromFavoriteUseCase: DeleteCryptoFromFavoriteUseCase,
    val userSetting: StoreUserSetting
) : ViewModel(), IListViewModel<FollowedCrypto> {

    private var _state = mutableStateOf(CryptoFollowedState())
    override val state: State<CryptoFollowedState> = _state

    init {
        getItems(userSetting.getCrypto())
    }

    override fun getItems(vararg args: Any) {
        getFavoriteCryptoUseCase(
            userId = userSetting.getUser().id
        ).onEach {
            when (it) {
                is Resource.Error ->
                    _state.value = CryptoFollowedState(error = it.asErrorUiText())

                is Resource.Loading -> _state.value = CryptoFollowedState(isLoading = true)
                is Resource.Success -> _state.value = CryptoFollowedState(items = it.data)
            }
        }.launchIn(viewModelScope)
    }

    fun delete(followedCrypto: FollowedCrypto) {
        deleteCryptoFromFavoriteUseCase(userSetting.getUser().id, followedCrypto.symbol).onEach {
            if (it is Resource.Error) {
                _state.value = CryptoFollowedState(error = (it.asErrorUiText()))
            }
        }.launchIn(viewModelScope)
        val list = _state.value.items.toMutableList()
        list.remove(followedCrypto)
        _state.value = _state.value.copy(items = list)
    }


}
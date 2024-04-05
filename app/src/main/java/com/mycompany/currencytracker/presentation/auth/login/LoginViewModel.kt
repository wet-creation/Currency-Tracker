package com.mycompany.currencytracker.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.user.UserLogin
import com.mycompany.currencytracker.domain.use_case.user.LoginUseCase
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val storeUserSetting: StoreUserSetting
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state


    fun sendLoginForm(userLogin: UserLogin) {
        loginUseCase(userLogin).onEach {
            when (it) {
                is Resource.Error ->
                    when (it.error) {
                        DataError.Network.NOT_FOUND -> {
                            _state.value =
                                LoginState(error = UiText.StringResource(R.string.user_not_found))
                        }

                        else -> {
                            val msg = it.asErrorUiText()
                            _state.value = LoginState(error = msg)
                        }
                    }

                is Resource.Loading -> _state.value = LoginState(isLoading = true)
                is Resource.Success -> {
                    storeUserSetting.saveUserParam(it.data)
                    _state.value = LoginState(result = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}
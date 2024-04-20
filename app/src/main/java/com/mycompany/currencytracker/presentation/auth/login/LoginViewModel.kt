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
import com.mycompany.currencytracker.presentation.common.emptyUiText
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

    private val _stateUserInput = mutableStateOf(UserInputLogin())
    val stateUserInput: State<UserInputLogin> = _stateUserInput


    fun emailOnChange(email: String) {
        _stateUserInput.value = _stateUserInput.value.copy(emailInput = email)
    }

    fun passwordOnChange(password: String) {
        _stateUserInput.value = _stateUserInput.value.copy(passwordInput = password)
    }

    fun changePasswordVisibility() {
        _stateUserInput.value =
            _stateUserInput.value.copy(isPasswordShown = !_stateUserInput.value.isPasswordShown)
    }

    fun dismissDialog() {
        _state.value = LoginState(httpError = emptyUiText)
    }

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
                            _state.value = LoginState(httpError = msg)
                        }
                    }

                is Resource.Loading -> _state.value = LoginState(isLoading = true)
                is Resource.Success -> {
                    storeUserSetting.saveUserParam(it.data.copy(password = _stateUserInput.value.passwordInput))
                    _state.value = LoginState(result = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}
package com.mycompany.currencytracker.presentation.auth.register


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.UserRegisterError
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.domain.use_case.user.RegisterUseCase
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.emptyUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state
    private val _stateUserInput = mutableStateOf(UserInputRegistration())
    val stateUserInput: State<UserInputRegistration> = _stateUserInput


    fun sendRegistrationForm(user: UserRegister) {
        registerUseCase.invoke(user).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = RegistrationState(isRegistry = true)
                }

                is Resource.Error -> {
                    when (it.error) {
                        UserRegisterError.Email.NOT_VALID -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(emailNotValid = true, formError = errorMsg)
                        }

                        DataError.Network.CONFLICT -> {
                            _state.value =
                                RegistrationState(
                                    emailNotValid = true, formError = UiText.StringResource(
                                        R.string.already_register
                                    )
                                )
                        }

                        UserRegisterError.Password.TOO_SHORT -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(passwordNotValid = true, formError = errorMsg)
                        }

                        UserRegisterError.Password.NO_UPPERCASE -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(passwordNotValid = true, formError = errorMsg)
                        }

                        UserRegisterError.Password.NO_DIGIT -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(passwordNotValid = true, formError = errorMsg)
                        }

                        UserRegisterError.Password.NO_LOWERCASE -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(passwordNotValid = true, formError = errorMsg)
                        }

                        UserRegisterError.Password.NOT_SAME -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(
                                    repeatPasswordNotValid = true,
                                    formError = errorMsg
                                )
                        }

                        UserRegisterError.TextInput.EMPTY -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(textInputNotValid = true, formError = errorMsg)
                        }

                        UserRegisterError.TextInput.TOO_LONG -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value =
                                RegistrationState(textInputNotValid = true, formError = errorMsg)
                        }

                        else -> {
                            val errorMsg = it.asErrorUiText()
                            _state.value = RegistrationState(httpError = errorMsg)
                        }
                    }
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onNameChange(string: String) {
        _stateUserInput.value = _stateUserInput.value.copy(nameInput = string)
    }

    fun onSurnameChange(string: String) {
        _stateUserInput.value = _stateUserInput.value.copy(surnameInput = string)
    }

    fun onEmailChange(string: String) {
        _stateUserInput.value = _stateUserInput.value.copy(emailInput = string)
    }

    fun onPasswordChange(string: String) {
        _stateUserInput.value = _stateUserInput.value.copy(passwordInput = string)
    }

    fun onRepeatPasswordChange(string: String) {
        _stateUserInput.value = _stateUserInput.value.copy(repeatPasswordInput = string)
    }

    fun changePasswordVisibility() {
        _stateUserInput.value =
            _stateUserInput.value.copy(isPasswordVisible = !_stateUserInput.value.isPasswordVisible)
    }

    fun dismissDialog() {
        _state.value = RegistrationState(httpError = emptyUiText    )
    }



}
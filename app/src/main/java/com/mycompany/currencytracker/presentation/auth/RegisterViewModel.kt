package com.mycompany.currencytracker.presentation.auth


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.UserRegisterError
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.domain.use_case.user.RegisterUseCase
import com.mycompany.currencytracker.presentation.common.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state


    fun sendRegistrationForm(user: UserRegister) {
        registerUseCase(user).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isRegistry = true)
                }

                is Resource.Error -> {
                    when(it.error) {
                        UserRegisterError.Email.NOT_VALID -> TODO()
                        UserRegisterError.Password.TOO_SHORT -> TODO()
                        UserRegisterError.Password.NO_UPPERCASE -> TODO()
                        UserRegisterError.Password.NO_DIGIT -> TODO()
                        UserRegisterError.Password.NO_LOWERCASE -> TODO()
                        UserRegisterError.Password.NOT_SAME -> TODO()
                        UserRegisterError.TextInput.EMPTY -> TODO()
                        UserRegisterError.TextInput.TOO_LONG -> TODO()
                        else -> {
                            val errorMsg = it.error.asUiText()
                            _state.value = RegistrationState(error = errorMsg)
                        }
                    }

                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }


}
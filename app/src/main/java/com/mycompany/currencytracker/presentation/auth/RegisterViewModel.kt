package com.mycompany.currencytracker.presentation.auth


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.domain.use_case.user.RegisterUseCase
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
                    _state.value =
                        _state.value.copy(error = it.message ?: "an unexpected error occurred")

                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }


}
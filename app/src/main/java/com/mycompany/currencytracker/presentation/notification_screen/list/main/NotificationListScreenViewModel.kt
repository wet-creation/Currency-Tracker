package com.mycompany.currencytracker.presentation.notification_screen.list.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.domain.model.user.notification.UserNotification
import com.mycompany.currencytracker.domain.use_case.user.DeleteNotificationUseCase
import com.mycompany.currencytracker.domain.use_case.user.GetNotificationsUseCase
import com.mycompany.currencytracker.presentation.common.asErrorUiText
import com.mycompany.currencytracker.presentation.common.list.IListState
import com.mycompany.currencytracker.presentation.common.list.IListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationListScreenViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val deleteNotification: DeleteNotificationUseCase,
    private val dataStore: StoreUserSetting
) : ViewModel(), IListViewModel<UserNotification> {

    private var _state = mutableStateOf(NotificationListScreenState())
    override val state: State<IListState<UserNotification>> = _state
    val baseCurrency = dataStore.getFiat()

    override fun getItems(vararg args: Any) {
        getNotificationsUseCase(dataStore.getUser().email, baseCurrency).onEach {
            _state.value = when (it) {
                is Resource.Error -> NotificationListScreenState(error = it.asErrorUiText())
                is Resource.Loading -> NotificationListScreenState(isLoading = true)
                is Resource.Success -> NotificationListScreenState(items = it.data)
            }
        }.launchIn(viewModelScope)
    }

    fun delete(userNotification: UserNotification) {
        deleteNotification(userNotification.id).onEach {
            if (it is Resource.Error) {
                _state.value = NotificationListScreenState(error = (it.asErrorUiText()))
            }
        }.launchIn(viewModelScope)
        val list = _state.value.items.toMutableList()
        list.remove(userNotification)
        _state.value = _state.value.copy(items = list)
    }
}
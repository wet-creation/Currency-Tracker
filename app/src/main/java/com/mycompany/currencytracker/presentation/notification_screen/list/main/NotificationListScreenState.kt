package com.mycompany.currencytracker.presentation.notification_screen.list.main

import com.mycompany.currencytracker.domain.model.user.notification.UserNotification
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.IListState

data class NotificationListScreenState(
    override val isLoading: Boolean = false,
    override val items: List<UserNotification> = emptyList(),
    override val error: UiText = emptyUiText
) :IListState<UserNotification>

package com.mycompany.currencytracker.presentation.notification_screen.list.select

import com.mycompany.currencytracker.domain.model.user.notification.CryptoSelectNotification
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.IListState

data class NotificationSelectCryptoScreenState(
    override val isLoading: Boolean = false,
    override val items: List<CryptoSelectNotification> = emptyList(),
    override val error: UiText = emptyUiText
): IListState<CryptoSelectNotification>

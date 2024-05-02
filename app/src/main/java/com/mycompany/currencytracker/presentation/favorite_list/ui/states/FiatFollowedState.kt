package com.mycompany.currencytracker.presentation.favorite_list.ui.states

import com.mycompany.currencytracker.domain.model.user.followed.FollowedFiat
import com.mycompany.currencytracker.presentation.common.UiText
import com.mycompany.currencytracker.presentation.common.emptyUiText
import com.mycompany.currencytracker.presentation.common.list.IListState

data class FiatFollowedState(
    override val isLoading: Boolean = false,
    override val items: List<FollowedFiat> = listOf(),
    override val error: UiText = emptyUiText

): IListState<FollowedFiat>

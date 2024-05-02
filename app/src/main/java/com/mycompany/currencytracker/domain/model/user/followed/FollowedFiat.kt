package com.mycompany.currencytracker.domain.model.user.followed

import com.mycompany.currencytracker.data.remote.dto.user.FiatFollowedDto
import com.mycompany.currencytracker.domain.model.currency.IFiat

data class FollowedFiat (
    val numberInList: Int,
    override val symbol: String,
    override val _24h: Double?,
    override val _7d: Double?,
    override val _30d: Double?,
    override val rate: Double,
): IFiat

fun FiatFollowedDto.toFiatFollowed() = FollowedFiat(numberInList, symbol, _24h, _7d, _1m, rate)
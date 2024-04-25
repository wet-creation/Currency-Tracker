package com.mycompany.currencytracker.domain.model.user

import com.mycompany.currencytracker.data.remote.dto.user.FiatFollowedListDto
import com.mycompany.currencytracker.data.remote.dto.user.FiatFollowedDto
import com.mycompany.currencytracker.domain.model.currency.IFiat

data class FollowedFiat (
    val userId: String,
    val numberInList: Int,
    override val symbol: String,
    override val _24h: Double?,
    override val _7d: Double?,
    override val _30d: Double?,
    override val rate: Double,
): IFiat {
    fun toCurrencyFollowedListDto() : FiatFollowedListDto {
        return FiatFollowedListDto(numberInList, userId, symbol)
    }
}

fun FiatFollowedDto.toFiatFollowed() = FollowedFiat(userId, numberInList, symbol, _24h, _7d, _1m, rate)
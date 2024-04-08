package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.domain.model.user.FollowedFiat
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetFavoriteFiatListUseCase @Inject constructor(
    val userFollowedRepository: UserFollowedRepository
) {
    operator fun invoke(userId: String) = flow<Resource<List<FollowedFiat>, DataError.Network>> {

    }
}
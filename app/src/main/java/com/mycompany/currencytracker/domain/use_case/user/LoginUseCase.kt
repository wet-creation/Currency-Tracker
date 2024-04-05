package com.mycompany.currencytracker.domain.use_case.user


import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.data.remote.dto.user.toUserLoginDto
import com.mycompany.currencytracker.domain.model.user.User
import com.mycompany.currencytracker.domain.model.user.UserLogin
import com.mycompany.currencytracker.domain.model.user.toUser
import com.mycompany.currencytracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userLoginForm: UserLogin) = flow<Resource<User, DataError.Network>> {
        try {
            emit(Resource.Loading())
            val user = userRepository.login(userLoginForm.toUserLoginDto())
            emit(Resource.Success(user.toUser()))
        } catch (e: HttpException) {
            when(e.code()) {
                404 -> emit(Resource.Error(DataError.Network.NOT_FOUND))
                500 -> emit(Resource.Error(DataError.Network.SERVER_ERROR))
            }
        } catch (e: IOException) {
            emit(Resource.Error(DataError.Network.NO_INTERNET))
        }

    }

}




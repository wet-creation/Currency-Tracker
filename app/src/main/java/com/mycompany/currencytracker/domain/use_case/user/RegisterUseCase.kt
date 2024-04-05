package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.UserRegisterError
import com.mycompany.currencytracker.common.debugLog
import com.mycompany.currencytracker.data.remote.dto.user.toUserDto
import com.mycompany.currencytracker.domain.model.user.UserRegister
import com.mycompany.currencytracker.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.regex.Pattern
import javax.inject.Inject

val EMAIL_ADDRESS: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
);

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(user: UserRegister): Flow<Resource<Unit, DataError>> = flow {
        try {
            emit(Resource.Loading())
            val validator = Validate()
            val validation = validator.validateUser(user)

            if (validation is Resource.Error) {
                emit(validation)
                debugLog(validation.toString())
                return@flow
            }
            userRepository.register(user.toUserDto())
            emit(validation)

        } catch (e: HttpException) {
            when (e.code()) {
                408 -> emit(Resource.Error(DataError.Network.REQUEST_TIMEOUT))
                413 -> emit(Resource.Error(DataError.Network.PAYLOAD_TOO_LARGE))
                503 -> emit(Resource.Error(DataError.Network.SERVER_ERROR))
                404 -> emit(Resource.Error(DataError.Network.NOT_FOUND))
                400 -> emit(Resource.Error(DataError.Network.BAD_REQUEST))
                409 -> emit(Resource.Error(DataError.Network.CONFLICT))
                else -> emit(Resource.Error(DataError.Network.UNKNOWN))
            }

        } catch (e: IOException) {
            emit(Resource.Error(DataError.Network.NO_INTERNET))
        }
    }

}


class Validate {
    fun validateUser(user: UserRegister): Resource<Unit, UserRegisterError> {
        val passwordValidate = passwordValidation(user.password, user.passwordRepeat)
        val nameValidate = validField(user.name)
        val surnameValidate = validField(user.surname)
        val emailValidate = validEmail(user.email)
        if (passwordValidate is Resource.Error)
            return passwordValidate
        if (nameValidate is Resource.Error)
            return nameValidate
        if (surnameValidate is Resource.Error)
            return surnameValidate
        if (emailValidate is Resource.Error)
            return emailValidate

        return Resource.Success(Unit)
    }

    private fun passwordValidation(
        password: String,
        passwordRepeat: String
    ): Resource<Unit, UserRegisterError.Password> {
        if (password.length < 8) return Resource.Error(UserRegisterError.Password.TOO_SHORT)
        if (password.firstOrNull { it.isDigit() } == null) return Resource.Error(UserRegisterError.Password.NO_DIGIT)
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isUpperCase() } == null) return Resource.Error(UserRegisterError.Password.NO_UPPERCASE)
        if (password.filter { it.isLetter() }
                .firstOrNull { it.isLowerCase() } == null) return Resource.Error(UserRegisterError.Password.NO_LOWERCASE)
        if (password != passwordRepeat) return Resource.Error(UserRegisterError.Password.NOT_SAME)
        return Resource.Success(Unit)
    }

    private fun validField(field: String): Resource<Unit, UserRegisterError.TextInput> {
        if (field.length > 20)
            return Resource.Error(UserRegisterError.TextInput.TOO_LONG)
        if (field.isEmpty())
            return Resource.Error(UserRegisterError.TextInput.EMPTY)
        return Resource.Success(Unit)
    }

    private fun validEmail(email: String): Resource<Unit, UserRegisterError.Email> {
        val valid = EMAIL_ADDRESS.matcher(email).matches()

        if (!valid)
            return Resource.Error(UserRegisterError.Email.NOT_VALID)


        return Resource.Success(Unit)
    }
}
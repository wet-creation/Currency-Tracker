package com.mycompany.currencytracker.domain.use_case.user

import com.mycompany.currencytracker.common.Resource
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
    operator fun invoke(user: UserRegister): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val validator = Validate()
            val validation = validator.validateUser(user)
            if (validation is Resource.Error)
                emit(validation)
            userRepository.register(user.toUserDto())
            emit(validation)

        } catch (e: HttpException) {
            emit(Resource.Error(e.code().toString()))
        } catch (e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }

}


class Validate {
    fun validateUser(user: UserRegister): Resource<Unit> {
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

    private fun passwordValidation(password: String, passwordRepeat: String): Resource<Unit> {
        if (password.length < 8) return Resource.Error("Password too short")
        if (password.firstOrNull { it.isDigit() } == null) return Resource.Error("Password doesn't have digits")
        if (password.filter { it.isLetter() }.firstOrNull { it.isUpperCase() } == null) return Resource.Error("Password doesn't have uppercase letters")
        if (password.filter { it.isLetter() }.firstOrNull { it.isLowerCase() } == null) return Resource.Error("Password doesn't have lowercase letters")
        if (password != passwordRepeat) return Resource.Error("Password doesn't match")
        return Resource.Success(Unit)
    }

    private fun validField(field: String): Resource<Unit> {
        if (field.length > 20)
            return Resource.Error("Too long")
        if (field.isEmpty())
            return Resource.Error("Field cannot be empty")
        return Resource.Success(Unit)
    }

    private fun validEmail(email: String): Resource<Unit> {
        val valid = EMAIL_ADDRESS.matcher(email).matches()

        if (!valid)
            return Resource.Error("Email address not valid")


        return Resource.Success(Unit)
    }
}
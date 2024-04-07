package com.mycompany.currencytracker.common

sealed interface Error

sealed interface DataError: Error {
    enum class Network: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        BAD_REQUEST,
        NOT_FOUND,
        CONFLICT,
        UNKNOWN
    }

}

sealed interface UserRegisterError: DataError {
    enum class Password : UserRegisterError {
        TOO_SHORT,
        NO_UPPERCASE,
        NO_DIGIT,
        NO_LOWERCASE,
        NOT_SAME
    }

    enum class Email : UserRegisterError {
        NOT_VALID
    }
    enum class TextInput : UserRegisterError {
        EMPTY,
        TOO_LONG
    }
}




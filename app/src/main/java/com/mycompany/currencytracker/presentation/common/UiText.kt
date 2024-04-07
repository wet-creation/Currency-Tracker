package com.mycompany.currencytracker.presentation.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mycompany.currencytracker.R
import com.mycompany.currencytracker.common.DataError
import com.mycompany.currencytracker.common.Resource
import com.mycompany.currencytracker.common.UserRegisterError

val emptyUiText = UiText.DynamicString("")

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(id, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )

        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.youve_hit_your_rate_limit
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.file_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Network.BAD_REQUEST -> UiText.StringResource(
            R.string.bad_request
        )

        DataError.Network.NOT_FOUND -> UiText.StringResource(
            R.string.not_found
        )
        DataError.Network.CONFLICT -> UiText.StringResource(
            R.string.the_conflict_occured
        )

        UserRegisterError.Email.NOT_VALID -> UiText.StringResource(
            R.string.email_wrong_enter
        )
        UserRegisterError.Password.TOO_SHORT -> UiText.StringResource(
            R.string.password_too_short
        )
        UserRegisterError.Password.NO_UPPERCASE -> UiText.StringResource(
            R.string.password_no_uppercase
        )
        UserRegisterError.Password.NO_DIGIT -> UiText.StringResource(
            R.string.password_no_digits
        )
        UserRegisterError.Password.NO_LOWERCASE -> UiText.StringResource(
            R.string.password_no_lowercase
        )
        UserRegisterError.Password.NOT_SAME -> UiText.StringResource(
            R.string.paswords_not_same
        )
        UserRegisterError.TextInput.EMPTY -> UiText.StringResource(
            R.string.text_input_empty
        )
        UserRegisterError.TextInput.TOO_LONG -> UiText.StringResource(
            R.string.text_input_too_long
        )
    }
}

fun Resource.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}
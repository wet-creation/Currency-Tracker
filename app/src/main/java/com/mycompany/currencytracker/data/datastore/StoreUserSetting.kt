package com.mycompany.currencytracker.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mycompany.currencytracker.domain.model.user.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StoreUserSetting
@Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("UserSetting")
        val USER_MAIN_CURRENCY_KEY = stringPreferencesKey("user_main_currency")
        val USER_MAIN_CRYPTO_KEY = stringPreferencesKey("user_main_crypto")

        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_SURNAME = stringPreferencesKey("user_surname")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PASSWORD = stringPreferencesKey("user_password")

        val USER_SELECTED_CHART_TIME = stringPreferencesKey("user_chart_time_string")
        val USER_IS_FIAT_SELECTED = booleanPreferencesKey("isFiat")

        val USER_THEME = intPreferencesKey("user_theme")
    }

    val getFiat: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_MAIN_CURRENCY_KEY] ?: "USD"
        }

    val getCrypto: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_MAIN_CRYPTO_KEY] ?: "btc"
        }

    val getChartTime: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_SELECTED_CHART_TIME] ?: "24h"
        }

    val isFiatSelected: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[USER_IS_FIAT_SELECTED] ?: true
        }

    val userTheme: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[USER_THEME] ?: Theme.SYSTEM.ordinal
        }

    fun getFiat(): String {
        var fiat: String
        runBlocking(Dispatchers.IO) {
            fiat = getFiat.first()
        }
        return fiat
    }

    fun getCrypto(): String {
        var crypto: String
        runBlocking(Dispatchers.IO) {
            crypto = getCrypto.first()
        }
        return crypto
    }

    suspend fun getChartTime(): String {
        return getChartTime.first()
    }

    suspend fun getSelectViewCurrency(): String {
        val currency = if (isFiatSelected.first()) {
            getFiat()
        } else {
            getCrypto()
        }
        return currency
    }

    suspend fun getSecondViewCurrency(): String {
        val currency: String = if (!isFiatSelected.first()) {
            getFiat()
        } else {
            getCrypto()
        }
        return currency
    }

    fun getTheme(): Theme {
        var theme: Theme
        runBlocking {
            theme = if (userTheme.first() == 0){
                Theme.SYSTEM
            }
            else if (userTheme.first() == 1){
                Theme.DARK
            }
            else{
                Theme.LIGHT
            }

        }

        return theme
    }

    fun getUser(): User {
        var user: User
        runBlocking {
            user = User(
                email = context.dataStore.data.map {
                    it[USER_EMAIL] ?: ""
                }.first(),
                id = context.dataStore.data.map {
                    it[USER_ID] ?: ""
                }.first(),
                name = context.dataStore.data.map {
                    it[USER_NAME] ?: ""
                }.first(),
                surname = context.dataStore.data.map {
                    it[USER_SURNAME] ?: ""
                }.first(),
                password = context.dataStore.data.map {
                    it[USER_PASSWORD] ?: ""
                }.first()
            )
        }
        return user
    }

    suspend fun saveUserParam(user: User) {
        context.dataStore.edit {
            it[USER_EMAIL] = user.email
            it[USER_ID] = user.id
            it[USER_NAME] = user.name
            it[USER_SURNAME] = user.surname
            it[USER_PASSWORD] = user.password
        }
    }

    suspend fun logoutUser() {
        context.dataStore.edit {
            it[USER_EMAIL] = ""
            it[USER_ID] = ""
            it[USER_NAME] = ""
            it[USER_SURNAME] = ""
            it[USER_PASSWORD] = ""
        }
    }

    suspend fun saveCurrency(sym: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_MAIN_CURRENCY_KEY] = sym
        }
    }

    suspend fun saveCrypto(sym: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_MAIN_CRYPTO_KEY] = sym
        }
    }

    suspend fun saveChartTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_SELECTED_CHART_TIME] = time
        }
    }

    suspend fun saveSelectViewCurrency(isFiat: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[USER_IS_FIAT_SELECTED] = isFiat
        }
    }

    fun saveTheme(theme: Theme) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[USER_THEME] = theme.ordinal
            }
        }
    }
}

enum class Theme(){
    SYSTEM, DARK, LIGHT
}
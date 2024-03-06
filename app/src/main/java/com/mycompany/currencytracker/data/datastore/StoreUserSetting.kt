package com.mycompany.currencytracker.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StoreUserSetting
    @Inject constructor(private val context: Context) {
    companion object{
        private val Context.dataStore by preferencesDataStore("UserSetting")
        val USER_MAIN_CURRENCY_KEY = stringPreferencesKey("user_main_currency")
    }

    val getCurrency: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_MAIN_CURRENCY_KEY] ?: "USD"
        }

    fun getCurrency(): String {
        var currency: String
        runBlocking(Dispatchers.IO) {
            currency = getCurrency.first()
        }
        return currency
    }

    suspend fun saveCurrency(sym: String){
        context.dataStore.edit{preferences ->
            preferences[USER_MAIN_CURRENCY_KEY] = sym
        }
    }
}
package com.mycompany.currencytracker.di

import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.data.remote.CurrencyTrackerApiCurrencies
import com.mycompany.currencytracker.data.repository.CurrenciesImp
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCurrencyTrackerApi(): CurrencyTrackerApiCurrencies {
        return Retrofit.Builder()
            .baseUrl(Constants.CURRENCY_TRACKER_REST_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyTrackerApiCurrencies::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyTrackerRepository(api: CurrencyTrackerApiCurrencies): CurrenciesRepository {
        return CurrenciesImp(api)
    }
}
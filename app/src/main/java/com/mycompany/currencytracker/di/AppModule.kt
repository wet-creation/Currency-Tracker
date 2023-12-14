package com.mycompany.currencytracker.di

import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.data.remote.CurrencyTrackerConvertService
import com.mycompany.currencytracker.data.remote.CurrencyTrackerCryptoService
import com.mycompany.currencytracker.data.remote.CurrencyTrackerCurrencyService
import com.mycompany.currencytracker.data.repository.ConvertIml
import com.mycompany.currencytracker.data.repository.CryptosIml
import com.mycompany.currencytracker.data.repository.CurrenciesImp
import com.mycompany.currencytracker.domain.repository.ConvertRepository
import com.mycompany.currencytracker.domain.repository.CryptosRepository
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
    fun provideRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    inline fun <reified T> provideCurrencyTrackerApi(clazz: Class<T>): T {
        return provideRetrofit(Constants.CURRENCY_TRACKER_REST_API_URL).create(T::class.java)
    }
    @Provides
    @Singleton
    fun provideCurrencyTrackerCurrencyService(): CurrencyTrackerCurrencyService {
        return provideCurrencyTrackerApi(CurrencyTrackerCurrencyService::class.java)
    }
    @Provides
    @Singleton
    fun provideCurrencyRepository(api: CurrencyTrackerCurrencyService): CurrenciesRepository {
        return CurrenciesImp(api)
    }

    @Provides
    @Singleton
    fun provideCurrencyTrackerCryptoService(): CurrencyTrackerCryptoService {
        return provideCurrencyTrackerApi(CurrencyTrackerCryptoService::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoRepository(api: CurrencyTrackerCryptoService): CryptosRepository {
        return CryptosIml(api)
    }

    @Provides
    @Singleton
    fun provideCurrencyTrackerConvertService(): CurrencyTrackerConvertService {
        return provideCurrencyTrackerApi(CurrencyTrackerConvertService::class.java)
    }

    @Provides
    @Singleton
    fun provideConvertRepository(api: CurrencyTrackerConvertService): ConvertRepository {
        return ConvertIml(api)
    }



}
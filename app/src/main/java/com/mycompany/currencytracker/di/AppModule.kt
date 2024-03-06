package com.mycompany.currencytracker.di

import android.content.Context
import com.mycompany.currencytracker.common.Constants
import com.mycompany.currencytracker.data.datastore.StoreUserSetting
import com.mycompany.currencytracker.data.remote.services.currency.CurrencyTrackerConvertService
import com.mycompany.currencytracker.data.remote.services.currency.CurrencyTrackerCryptoService
import com.mycompany.currencytracker.data.remote.services.currency.CurrencyTrackerCurrencyService
import com.mycompany.currencytracker.data.remote.services.user.UserService
import com.mycompany.currencytracker.data.remote.services.user.UserServiceFollowed
import com.mycompany.currencytracker.data.remote.services.user.UserServiceNotification
import com.mycompany.currencytracker.data.repository.ConvertIml
import com.mycompany.currencytracker.data.repository.CryptosIml
import com.mycompany.currencytracker.data.repository.CurrenciesImp
import com.mycompany.currencytracker.data.repository.UserFollowedImpl
import com.mycompany.currencytracker.data.repository.UserNotificationImpl
import com.mycompany.currencytracker.data.repository.Userimpl
import com.mycompany.currencytracker.domain.repository.ConvertRepository
import com.mycompany.currencytracker.domain.repository.CryptosRepository
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import com.mycompany.currencytracker.domain.repository.UserNotificationRepository
import com.mycompany.currencytracker.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideUserService(): UserService {
        return provideCurrencyTrackerApi(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserService): UserRepository {
        return Userimpl(api)
    }

    @Provides
    @Singleton
    fun provideUserFollowedService(): UserServiceFollowed {
        return provideCurrencyTrackerApi(UserServiceFollowed::class.java)
    }

    @Provides
    @Singleton
    fun provideUserFollowedRepository(api: UserServiceFollowed): UserFollowedRepository {
        return UserFollowedImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserNotificationService(): UserServiceNotification {
        return provideCurrencyTrackerApi(UserServiceNotification::class.java)
    }

    @Provides
    @Singleton
    fun provideUserNotificationRepository(api: UserServiceNotification): UserNotificationRepository {
        return UserNotificationImpl(api)
    }

    @Singleton
    @Provides
    fun provideUserSettings(@ApplicationContext appContext: Context): StoreUserSetting {
        return StoreUserSetting(appContext)
    }

}
package com.mycompany.currencytracker.di

import com.mycompany.currencytracker.common.Constants.CURRENCY_TRACKER_REST_API_URL
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
import com.mycompany.currencytracker.data.repository.UserImpl
import com.mycompany.currencytracker.data.repository.UserNotificationImpl
import com.mycompany.currencytracker.domain.repository.ConvertRepository
import com.mycompany.currencytracker.domain.repository.CryptosRepository
import com.mycompany.currencytracker.domain.repository.CurrenciesRepository
import com.mycompany.currencytracker.domain.repository.UserFollowedRepository
import com.mycompany.currencytracker.domain.repository.UserNotificationRepository
import com.mycompany.currencytracker.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    companion object {
        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(CURRENCY_TRACKER_REST_API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        inline fun <reified T> provideCurrencyTrackerApi(retrofit: Retrofit, clazz: Class<T>): T {
            return retrofit.create(T::class.java)
        }

        @Provides
        @Singleton
        fun provideCurrencyTrackerCurrencyService(retrofit: Retrofit): CurrencyTrackerCurrencyService {
            return provideCurrencyTrackerApi(retrofit, CurrencyTrackerCurrencyService::class.java)
        }

        @Provides
        @Singleton
        fun provideCurrencyRepository(api: CurrencyTrackerCurrencyService): CurrenciesRepository {
            return CurrenciesImp(api)
        }

        @Provides
        @Singleton
        fun provideCurrencyTrackerCryptoService(retrofit: Retrofit): CurrencyTrackerCryptoService {
            return provideCurrencyTrackerApi(retrofit, CurrencyTrackerCryptoService::class.java)
        }

        @Provides
        @Singleton
        fun provideCryptoRepository(api: CurrencyTrackerCryptoService): CryptosRepository {
            return CryptosIml(api)
        }

        @Provides
        @Singleton
        fun provideCurrencyTrackerConvertService(retrofit: Retrofit): CurrencyTrackerConvertService {
            return provideCurrencyTrackerApi(retrofit, CurrencyTrackerConvertService::class.java)
        }

        @Provides
        @Singleton
        fun provideConvertRepository(api: CurrencyTrackerConvertService): ConvertRepository {
            return ConvertIml(api)
        }

        @Provides
        @Singleton
        fun provideUserService(retrofit: Retrofit): UserService {
            return provideCurrencyTrackerApi(retrofit, UserService::class.java)
        }

        @Provides
        @Singleton
        fun provideUserRepository(api: UserService): UserRepository {
            return UserImpl(api)
        }

        @Provides
        @Singleton
        fun provideUserFollowedService(retrofit: Retrofit): UserServiceFollowed {
            return provideCurrencyTrackerApi(retrofit, UserServiceFollowed::class.java)
        }

        @Provides
        @Singleton
        fun provideUserFollowedRepository(api: UserServiceFollowed): UserFollowedRepository {
            return UserFollowedImpl(api)
        }

        @Provides
        @Singleton
        fun provideUserNotificationService(retrofit: Retrofit): UserServiceNotification {
            return provideCurrencyTrackerApi(retrofit, UserServiceNotification::class.java)
        }

        @Provides
        @Singleton
        fun provideUserNotificationRepository(api: UserServiceNotification): UserNotificationRepository {
            return UserNotificationImpl(api)
        }
    }

}
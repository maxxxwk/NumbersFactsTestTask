package com.maxxxwk.mornhousetesttask.di

import com.maxxxwk.mornhousetesttask.BuildConfig
import com.maxxxwk.mornhousetesttask.screens.main.data.remote.NumbersApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideNumberApiService(): NumbersApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.NUMBERS_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NumbersApiService::class.java)
    }
}

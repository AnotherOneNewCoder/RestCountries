package com.zhogin.restcountries.di

import com.zhogin.restcountries.data.api.AppHttpClient
import com.zhogin.restcountries.data.api.service.CountriesApiService
import com.zhogin.restcountries.data.api.service.CountriesApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesHttpClient(httpClient: AppHttpClient): HttpClient = httpClient.getHttpClient()

    @Provides
    @Singleton
    fun providesCountriesApiService(client: HttpClient): CountriesApiService =
        CountriesApiServiceImpl(client)

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class BaseUrl
}
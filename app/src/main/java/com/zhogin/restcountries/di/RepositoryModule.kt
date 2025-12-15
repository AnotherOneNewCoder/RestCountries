package com.zhogin.restcountries.di

import com.zhogin.restcountries.data.api.mapper.CountryDtoMapper
import com.zhogin.restcountries.data.cache.mapper.CountryEntityMapper
import com.zhogin.restcountries.data.repository.CountryRepositoryImpl
import com.zhogin.restcountries.domain.repository.CountryRepository
import com.zhogin.restcountries.domain.usecase.GetCountriesUseCase
import com.zhogin.restcountries.domain.usecase.GetCountyDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCountryRepository(repositoryImpl: CountryRepositoryImpl): CountryRepository

}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetCountriesUseCase(repository: CountryRepository): GetCountriesUseCase {
        return GetCountriesUseCase(repository)
    }
    @Provides
    fun provideGetCountryDetailsUseCase(repository: CountryRepository): GetCountyDetailsUseCase {
        return GetCountyDetailsUseCase(repository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {
    @Provides
    @Singleton
    fun provideCountryDtoMapper() = CountryDtoMapper()

    @Provides
    @Singleton
    fun provideCountryEntityMapper() = CountryEntityMapper()

}
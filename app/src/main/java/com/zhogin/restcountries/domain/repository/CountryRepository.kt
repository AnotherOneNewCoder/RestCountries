package com.zhogin.restcountries.domain.repository

import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getCountries(forceRefresh: Boolean = false): Flow<Resource<List<Country>>>
    // метод для получения деталей страны будет брать из кэша
    suspend fun getCountryDetails(name: String): Resource<Country>
}
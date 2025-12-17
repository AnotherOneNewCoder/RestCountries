package com.zhogin.restcountries.data.api.service

import com.zhogin.restcountries.data.api.model.CountryDto

interface CountriesApiService {
    suspend fun getAllCountries(): List<CountryDto>
}
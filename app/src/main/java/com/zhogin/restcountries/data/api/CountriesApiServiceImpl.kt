package com.zhogin.restcountries.data.api

import com.zhogin.restcountries.data.api.model.CountryDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject


private const val BASE_URL = "https://restcountries.com/v3.1"
private const val FIELDS = "name,flags,capital,population,currencies,region"

class CountriesApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : CountriesApiService {
    override suspend fun getAllCountries(): List<CountryDto> {
        return client.get("$BASE_URL/all") {
            parameter("fields", FIELDS)
        }.body()
    }
}
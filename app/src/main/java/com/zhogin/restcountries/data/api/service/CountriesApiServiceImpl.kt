package com.zhogin.restcountries.data.api.service

import com.zhogin.restcountries.data.api.model.CountryDto
import com.zhogin.restcountries.di.NetworkModule
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject


private const val FIELDS = "name,flags,capital,population,currencies,region"

class CountriesApiServiceImpl @Inject constructor(
    private val client: HttpClient,
    @NetworkModule.BaseUrl private val baseUrl: String = "https://restcountries.com/v3.1"
) : CountriesApiService {
    override suspend fun getAllCountries(): List<CountryDto> {
        //return client.get("$BASE_URL/all") {
        return client.get("$baseUrl/all") {
            parameter("fields", FIELDS)
        }.body()
    }
}
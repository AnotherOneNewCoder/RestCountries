package com.zhogin.restcountries.data.cache.mapper

import com.zhogin.restcountries.data.cache.entity.CountryEntity
import com.zhogin.restcountries.domain.model.Country
import javax.inject.Inject

class CountryEntityMapper @Inject constructor() {
    fun mapToDomain(entity: CountryEntity): Country = Country(
        name = entity.name,
        flagUri = entity.flagUri,
        capital = entity.capital,
        population = entity.population,
        region = entity.region,
    )

    fun mapListToDomain(entities: List<CountryEntity>): List<Country> {
        return entities.map(::mapToDomain)
    }
}
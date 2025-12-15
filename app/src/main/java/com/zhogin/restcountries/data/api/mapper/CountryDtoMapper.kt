package com.zhogin.restcountries.data.api.mapper

import com.zhogin.restcountries.data.api.model.CountryDto
import com.zhogin.restcountries.data.cache.entity.CountryEntity
import javax.inject.Inject

class CountryDtoMapper @Inject constructor() {
    fun mapToEntity(dto: CountryDto): CountryEntity {
        return CountryEntity(
            name = dto.name.common,
            flagUri = dto.flags.png,
            capital = dto.capital?.firstOrNull(),
            population = dto.population,
            region = dto.region,
        )
    }

    fun mapListToEntity(dtos: List<CountryDto>): List<CountryEntity> {
        return dtos.map(::mapToEntity)
    }
}
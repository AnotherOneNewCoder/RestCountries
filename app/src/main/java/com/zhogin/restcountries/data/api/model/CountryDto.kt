package com.zhogin.restcountries.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String>? = null,
    val population: Long? = null,
    val region: String? = null,
)


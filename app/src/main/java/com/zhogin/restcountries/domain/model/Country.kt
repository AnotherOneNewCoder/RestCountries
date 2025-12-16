package com.zhogin.restcountries.domain.model

data class Country(
    val name: String,
    val flagUri: String,
    val capital: String?,
    val population: Long?,
    val region: String?,
)
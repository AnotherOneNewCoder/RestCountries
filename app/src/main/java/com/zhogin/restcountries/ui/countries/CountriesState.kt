package com.zhogin.restcountries.ui.countries

import com.zhogin.restcountries.domain.model.Country

data class CountriesState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val showErrorText: Boolean = false
)
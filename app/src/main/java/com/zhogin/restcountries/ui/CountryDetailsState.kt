package com.zhogin.restcountries.ui

import com.zhogin.restcountries.domain.model.Country

data class CountryDetailsState(
    val country: Country? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val showErrorText: Boolean = false
)
package com.zhogin.restcountries.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object CountriesList: Route, NavKey

    @Serializable
    data class CountryDetails(val name: String): Route, NavKey
}
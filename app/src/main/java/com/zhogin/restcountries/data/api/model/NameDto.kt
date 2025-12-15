package com.zhogin.restcountries.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    val common: String
)
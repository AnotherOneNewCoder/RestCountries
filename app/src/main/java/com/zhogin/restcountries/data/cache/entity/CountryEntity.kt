package com.zhogin.restcountries.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val name: String,
    val flagUri: String,
    val capital: String?,
    val population: Long?,
    val region: String?,
    val timestamp: Long = System.currentTimeMillis(),
)
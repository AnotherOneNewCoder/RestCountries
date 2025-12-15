package com.zhogin.restcountries.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhogin.restcountries.data.cache.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM COUNTRIES ORDER BY name ASC")
    fun getAllCountries(): Flow<List<CountryEntity>>

    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()

    @Query("SELECT * FROM countries WHERE name = :name")
    suspend fun getCountryByName(name: String): CountryEntity?
}
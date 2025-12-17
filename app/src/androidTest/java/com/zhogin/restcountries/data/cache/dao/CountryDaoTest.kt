package com.zhogin.restcountries.data.cache.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.zhogin.restcountries.data.cache.CountriesRoomDatabase
import com.zhogin.restcountries.data.cache.entity.CountryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CountryDaoTest {

    private lateinit var db: CountriesRoomDatabase
    private lateinit var dao: CountryDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Создаем CountriesRoomDatabase в оперативной памяти
        db = Room.inMemoryDatabaseBuilder(
            context,
            CountriesRoomDatabase::class.java
        ).build()

        dao = db.countryDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadCountry() = runTest {

        val country = CountryEntity(
            name = "Ukraine",
            flagUri = "https://example.com/ua.png",
            capital = "Kyiv",
            population = 41000000L,
            region = "Europe"
        )

        dao.insertCountries(listOf(country))
        val result = dao.getCountryByName("Ukraine")


        assertEquals("Ukraine", result?.name)
        assertEquals("Kyiv", result?.capital)
    }

    @Test
    fun getAllCountries_ShouldReturnSortedList() = runTest {

        val countries = listOf(
            CountryEntity("Germany", "uri", "Berlin", 83000000L, "Europe"),
            CountryEntity("Albania", "uri", "Tirana", 2800000L, "Europe")
        )


        dao.insertCountries(countries)
        val result = dao.getAllCountries().first()

        // Then: Проверяем сортировку ORDER BY name ASC
        assertEquals(2, result.size)
        assertEquals("Albania", result[0].name)
        assertEquals("Germany", result[1].name)
    }

    @Test
    fun deleteCountries_ShouldClearDatabase() = runTest {

        val country = CountryEntity("Japan", "uri", "Tokyo", 125000000L, "Asia")
        dao.insertCountries(listOf(country))

        dao.deleteAllCountries()
        val result = dao.getAllCountries().first()

        assertTrue(result.isEmpty())
    }
}
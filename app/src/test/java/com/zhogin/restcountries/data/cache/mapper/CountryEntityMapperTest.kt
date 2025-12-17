package com.zhogin.restcountries.data.cache.mapper

import com.zhogin.restcountries.data.cache.entity.CountryEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CountryEntityMapperTest {

    private lateinit var mapper: CountryEntityMapper

    @Before
    fun setUp() {
        mapper = CountryEntityMapper()
    }

    @Test
    fun `mapToDomain should correctly map entity to domain model`() {

        val entity = CountryEntity(
            name = "Ukraine",
            flagUri = "https://example.com/ua.png",
            capital = "Kyiv",
            population = 41000000L,
            region = "Europe"
        )

        val result = mapper.mapToDomain(entity)

        assertEquals(entity.name, result.name)
        assertEquals(entity.flagUri, result.flagUri)
        assertEquals(entity.capital, result.capital)
        assertEquals(entity.population, result.population)
        assertEquals(entity.region, result.region)
    }

    @Test
    fun `mapListToDomain should correctly map list of entities`() {
        val entities = listOf(
            CountryEntity("France", "uri1", "Paris", 67000000L, "Europe"),
            CountryEntity("Japan", "uri2", "Tokyo", 125000000L, "Asia")
        )

        val result = mapper.mapListToDomain(entities)

        // Then
        assertEquals(2, result.size)
        assertEquals("France", result[0].name)
        assertEquals("Japan", result[1].name)
        assertEquals("Paris", result[0].capital)
        assertEquals("Tokyo", result[1].capital)
    }

    @Test
    fun `mapToDomain should handle null values correctly`() {
        //создаем сущность, где nullable поля равны null
        val entity = CountryEntity(
            name = "Unknown Land",
            flagUri = "https://example.com/none.png",
            capital = null,
            population = null,
            region = null
        )

        val result = mapper.mapToDomain(entity)

        assertEquals("Unknown Land", result.name)
        assertEquals(null, result.capital)
        assertEquals(null, result.population)
    }
}
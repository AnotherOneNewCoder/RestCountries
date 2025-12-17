package com.zhogin.restcountries.data.api.mapper

import com.zhogin.restcountries.data.api.model.CountryDto
import com.zhogin.restcountries.data.api.model.FlagsDto
import com.zhogin.restcountries.data.api.model.NameDto
import com.zhogin.restcountries.data.cache.entity.CountryEntity
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

// Сделаю тесты только на удачное выполнение. Не вижу смысла в других, т.к. приложение очень простое.

class CountryDtoMapperTest {

    private lateinit var mapper: CountryDtoMapper

    @Before
    fun setUp() {
        mapper = CountryDtoMapper()
    }
    @Test
    fun `mapToEntity maps all fields correctly when capital is present`() {

        val dto = CountryDto(
            name = NameDto(common = "Canada"),
            flags = FlagsDto(png = "http://canada_flag.png"),
            capital = listOf("Ottawa"),
            population = 38000000L,
            region = "Americas"
        )

        val expectedEntity = CountryEntity(
            name = "Canada",
            flagUri = "http://canada_flag.png",
            capital = "Ottawa",
            population = 38000000L,
            region = "Americas"
        )


        val resultEntity = mapper.mapToEntity(dto)


        assertEquals(expectedEntity, resultEntity)
    }

    @Test
    fun `mapToEntity handles null or empty capital list correctly resulting in null capital`() {
        // Подготовка данных для двух крайних случаев: capital = null и capital = emptyList()
        val dtoWithNullCapital = CountryDto(
            name = NameDto(common = "Country A"),
            flags = FlagsDto(png = "url_a"),
            capital = null, // Кейс 1: null
            population = 1L,
            region = "Region A"
        )

        val dtoWithEmptyCapitalList = CountryDto(
            name = NameDto(common = "Country B"),
            flags = FlagsDto(png = "url_b"),
            capital = emptyList(), // Кейс 2: пустой список
            population = 2L,
            region = "Region B"
        )


        assertEquals(null, mapper.mapToEntity(dtoWithNullCapital).capital)
        assertEquals(null, mapper.mapToEntity(dtoWithEmptyCapitalList).capital)
    }

    @Test
    fun `mapListToEntity maps a list of DTOs correctly`() {

        val dtoList = listOf(
            CountryDto(
                name = NameDto(common = "C1"),
                flags = FlagsDto(png = "u1"),
                capital = listOf("Cap1"),
                population = 1,
                region = "R1"
            ),
            CountryDto(
                name = NameDto(common = "C2"),
                flags = FlagsDto(png = "u2"),
                capital = listOf("Cap2"),
                population = 2,
                region = "R2"
            )
        )

        val expectedEntityList = listOf(
            CountryEntity(name = "C1", flagUri = "u1", capital = "Cap1", population = 1, region = "R1"),
            CountryEntity(name = "C2", flagUri = "u2", capital = "Cap2", population = 2, region = "R2")
        )

        val resultEntityList = mapper.mapListToEntity(dtoList)

        assertEquals(expectedEntityList, resultEntityList)
        assertEquals(2, resultEntityList.size)
    }
}
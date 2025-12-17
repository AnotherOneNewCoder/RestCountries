package com.zhogin.restcountries.data.repository


import org.junit.Test

import app.cash.turbine.test
import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.data.api.mapper.CountryDtoMapper
import com.zhogin.restcountries.data.api.model.CountryDto
import com.zhogin.restcountries.data.api.model.FlagsDto
import com.zhogin.restcountries.data.api.model.NameDto
import com.zhogin.restcountries.data.api.service.CountriesApiService
import com.zhogin.restcountries.data.cache.CountriesDatabase
import com.zhogin.restcountries.data.cache.dao.CountryDao
import com.zhogin.restcountries.data.cache.entity.CountryEntity
import com.zhogin.restcountries.data.cache.mapper.CountryEntityMapper
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before


class CountryRepositoryImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()
    private val api = mockk<CountriesApiService>()
    private val database = mockk<CountriesDatabase>()
    private val dao = mockk<CountryDao>()
    private val dtoMapper = CountryDtoMapper() // Можно использовать реальный, т.к. он простой
    private val entityMapper = CountryEntityMapper()

    private lateinit var repository: CountryRepositoryImpl

    @Before
    fun setUp() {
        // Связываем dao с моком базы данных
        every { database.countryDao } returns dao
        repository = CountryRepositoryImpl(api, database, dtoMapper, entityMapper, testDispatcher)
    }

    @Test
    fun `getCountries should emit Loading then Success when API call is successful`() = runTest {

        val remoteDto = CountryDto(
            name = NameDto("Ukraine"),
            flags = FlagsDto("ua_png"),
            capital = listOf("Kyiv"),
            population = 41000000L,
            region = "Europe"
        )
        val entity = dtoMapper.mapToEntity(remoteDto)

        // Мокаем пустое состояние базы в начале
        coEvery { dao.getAllCountries() } returnsMany listOf(
            flowOf(emptyList()), // Первый вызов: кэша нет
            flowOf(listOf(entity)) // Второй вызов: данные после сохранения
        )
        coEvery { api.getAllCountries() } returns listOf(remoteDto)
        coEvery { dao.deleteAllCountries() } just Runs
        coEvery { dao.insertCountries(any()) } just Runs

        // --- When & Then (Вызов и проверка через Turbine) ---
        repository.getCountries(forceRefresh = true).test {
            // 1. Сначала должен прийти Loading без данных (т.к. кэш пуст)
            val firstItem = awaitItem()
            assert(firstItem is Resource.Loading && firstItem.data == null)

            // 2. Затем должен прийти Success с данными из API
            val secondItem = awaitItem()
            assert(secondItem is Resource.Success)
            assertEquals("Ukraine", secondItem.data?.first()?.name)

            awaitComplete()
        }

        // Проверяем, что методы вызывались в нужном порядке
        coVerify(exactly = 1) { api.getAllCountries() }
        coVerify(exactly = 1) { dao.deleteAllCountries() }
        coVerify(exactly = 1) { dao.insertCountries(any()) }
    }

    @Test
    fun `getCountries should emit Loading with cache and then Error when API fails`() = runTest {

        val cachedEntity = CountryEntity("Norway", "uri", "Oslo", 5000000L, "Europe")

        coEvery { dao.getAllCountries() } returns flowOf(listOf(cachedEntity))
        coEvery { api.getAllCountries() } throws Exception("Network error")

        repository.getCountries(forceRefresh = false).test {
            // 1. Должен прийти Loading с данными из кэша
            val loadingItem = awaitItem()
            assertEquals("Norway", loadingItem.data?.first()?.name)

            // 2. Должен прийти Error, но с данными из кэша
            val errorItem = awaitItem()
            assert(errorItem is Resource.Error)
            assertEquals("Norway", errorItem.data?.first()?.name)
            assert(errorItem.message?.contains("Update error") == true)

            awaitComplete()
        }
    }

    @Test
    fun `getCountryDetails should return Success when country exists in cache`() = runTest {

        val entity = CountryEntity("Japan", "uri", "Tokyo", 125000000L, "Asia")
        coEvery { dao.getCountryByName("Japan") } returns entity

        val result = repository.getCountryDetails("Japan")

        assert(result is Resource.Success)
        assertEquals("Japan", result.data?.name)
    }
}
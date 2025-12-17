package com.zhogin.restcountries.ui.countries

import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.domain.usecase.GetCountriesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesViewModelTest {

    private val getCountriesUseCase = mockk<GetCountriesUseCase>()
    private lateinit var viewModel: CountriesViewModel

    // Тестовый диспетчер для замены Dispatchers.Main
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should call loadCountries and update state to Success`() = runTest {

        val countries = listOf(mockk<Country>(relaxed = true))
        every { getCountriesUseCase(false) } returns flowOf(
            Resource.Loading(null),
            Resource.Success(countries)
        )

        viewModel = CountriesViewModel(getCountriesUseCase)

        assertEquals(countries, viewModel.state.countries)
        assertFalse(viewModel.state.isLoading)
        assertNull(viewModel.state.error)
    }

    @Test
    fun `loadCountries should update state to Error when usecase returns error`() = runTest {

        every { getCountriesUseCase(false) } returns flowOf(Resource.Loading(null))
        val errorMessage = "Network Error"
        every { getCountriesUseCase(true) } returns flowOf(
            Resource.Loading(null),
            Resource.Error(errorMessage, null)
        )

        viewModel = CountriesViewModel(getCountriesUseCase) // Вызовет init(false)
        viewModel.loadCountries(true) // Вызываем явно для теста

        assertEquals(errorMessage, viewModel.state.error)
        assertFalse(viewModel.state.isLoading)
        assertTrue(viewModel.state.showErrorText)
    }

    @Test
    fun `loading with existing data should not set isLoading to true`() = runTest {

        val cachedCountries = listOf(mockk<Country>(relaxed = true))
        every { getCountriesUseCase(false) } returns flowOf(
            Resource.Loading(cachedCountries)
        )

        viewModel = CountriesViewModel(getCountriesUseCase)

        assertEquals(cachedCountries, viewModel.state.countries)
        assertFalse(viewModel.state.isLoading) // isLoading только если данных совсем нет
    }
}
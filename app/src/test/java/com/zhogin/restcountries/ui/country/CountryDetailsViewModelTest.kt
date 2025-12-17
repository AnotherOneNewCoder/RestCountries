package com.zhogin.restcountries.ui.country

import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.domain.usecase.GetCountyDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailsViewModelTest {

    private val getCountyDetailsUseCase = mockk<GetCountyDetailsUseCase>()
    private lateinit var viewModel: CountryDetailsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CountryDetailsViewModel(getCountyDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCountryFromDb should update state to Success when country found`() = runTest {

        val countryName = "Italy"
        val mockCountry = Country(
            name = countryName,
            flagUri = "uri",
            capital = "Rome",
            population = 60000000L,
            region = "Europe"
        )
        // Используем coEvery, так как это suspend функция
        coEvery { getCountyDetailsUseCase(countryName) } returns Resource.Success(mockCountry)

        viewModel.loadCountryFromDb(countryName)

        assertEquals(mockCountry, viewModel.state.country)
        assertFalse(viewModel.state.isLoading)
        assertNull(viewModel.state.error)
        assertFalse(viewModel.state.showErrorText)
    }

    @Test
    fun `loadCountryFromDb should update state to Error when country not found`() = runTest {

        val countryName = "Atlantis"
        val errorMessage = "Country not found in cache"
        coEvery { getCountyDetailsUseCase(countryName) } returns Resource.Error(errorMessage, null)

        viewModel.loadCountryFromDb(countryName)

        assertNull(viewModel.state.country)
        assertFalse(viewModel.state.isLoading)
        assertEquals(errorMessage, viewModel.state.error)
        assertEquals(true, viewModel.state.showErrorText)
    }
}
package com.zhogin.restcountries.ui.countries.screen



import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.ui.countries.CountriesState
import org.junit.Rule
import org.junit.Test

class CountriesListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsLoader() {
        // Given
        val state = CountriesState(isLoading = true, countries = emptyList())

        composeTestRule.setContent {
            CountriesListContent(
                state = state,
                onCountryClick = {},
                onRefresh = {}
            )
        }

        // Then: Ищем по тегу, который мы добавили в Modifier
        composeTestRule.onNodeWithTag("loader").assertIsDisplayed()
    }

    @Test
    fun successState_showsCountriesList() {
        // Given
        val countries = listOf(
            Country("Italy", "uri", "Rome", 60000000L, "Europe")
        )
        val state = CountriesState(isLoading = false, countries = countries)

        composeTestRule.setContent {
            CountriesListContent(state = state, onCountryClick = {}, onRefresh = {})
        }

        // Then
        composeTestRule.onNodeWithText("Italy").assertIsDisplayed()
        // Проверяем, что лоадера нет
        composeTestRule.onNodeWithTag("loader").assertDoesNotExist()
    }

    @Test
    fun errorState_showsMessage() {
        // Given
        val errorMsg = "Network Error"
        val state = CountriesState(
            showErrorText = true,
            error = errorMsg,
            isLoading = false,
            countries = emptyList()
        )

        composeTestRule.setContent {
            CountriesListContent(state = state, onCountryClick = {}, onRefresh = {})
        }

        // Then
        composeTestRule.onNodeWithText(errorMsg).assertIsDisplayed()
    }

    @Test
    fun countryItem_click_triggersCallbackWithCorrectData() {
        // Given: список с одной страной
        val countryName = "Italy"
        val country = Country(
            name = countryName,
            flagUri = "uri",
            capital = "Rome",
            population = 60000000L,
            region = "Europe"
        )
        val state = CountriesState(countries = listOf(country))

        var capturedName: String? = null

        composeTestRule.setContent {
            CountriesListContent(
                state = state,
                onCountryClick = { capturedName = it }, // Сохраняем результат клика
                onRefresh = {}
            )
        }

        // When: Кликаем по карточке, используя наш новый динамический тег
        composeTestRule
            .onNodeWithTag("country_card_$countryName")
            .performClick()

        // Then: Проверяем, что в callback пришло именно то имя, которое мы ожидали
        assert(capturedName == countryName)
    }
}
package com.zhogin.restcountries.ui.country.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.ui.country.CountryDetailsState
import org.junit.Rule
import org.junit.Test

class CountryDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun countryDetails_displaysAllInfoCorrectly() {

        val testCountry = Country(
            name = "France",
            flagUri = "https://example.com/flag.png",
            capital = "Paris",
            population = 67000000L,
            region = "Europe"
        )
        val state = CountryDetailsState(
            country = testCountry,
            isLoading = false
        )

        composeTestRule.setContent {
            CountryDetailsContent(
                state = state,
                onRetry = {}
            )
        }
        val expectedContentDescription = "Flag ${testCountry.name}"

        // Проверяем названия
        composeTestRule.onNodeWithText("France").assertIsDisplayed()
        composeTestRule.onNodeWithText("Paris").assertIsDisplayed()
        composeTestRule.onNodeWithText("Europe").assertIsDisplayed()
        composeTestRule.onNodeWithText("67000000").assertIsDisplayed()

        // Два одиннаковых описания
        composeTestRule.onAllNodesWithContentDescription(expectedContentDescription).assertCountEquals(2)
        composeTestRule.onAllNodesWithContentDescription(expectedContentDescription)[0].assertExists()
        composeTestRule.onAllNodesWithContentDescription(expectedContentDescription)[1].assertExists()
    }

    @Test
    fun countryDetails_showsLabelsAndValues() {
        val state = CountryDetailsState(
            country = Country("France", "", "Paris", 100, "Europe")
        )

        composeTestRule.setContent {
            CountryDetailsContent(state = state, onRetry = {})
        }

        // Проверяем наличие меток (Labels)
        composeTestRule.onNodeWithText("Capital").assertIsDisplayed()
        composeTestRule.onNodeWithText("Paris").assertIsDisplayed()

        composeTestRule.onNodeWithText("Region").assertIsDisplayed()
        composeTestRule.onNodeWithText("Europe").assertIsDisplayed()

        composeTestRule.onNodeWithText("Population").assertIsDisplayed()
        composeTestRule.onNodeWithText("100").assertIsDisplayed()
    }

}
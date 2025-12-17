package com.zhogin.restcountries.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.ui.countries.CountriesState
import com.zhogin.restcountries.ui.countries.screen.CountriesListContent
import org.junit.Rule
import org.junit.Test

class NavigationRootTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appNavigation_clickOnCountry_opensDetails() {
        // Имитируем стэк, как в твоем NavDisplay
        val backStack = mutableStateListOf<Route>(Route.CountriesList)
        val state = CountriesState(
            countries = listOf(
                Country(
                    name = "France",
                    flagUri = "",
                    capital = "Paris",
                    population = 100000,
                    region = "Europe",
                ),
                Country(
                    name = "Russia",
                    flagUri = "",
                    capital = "Moscow",
                    population = 146000000,
                    region = "Europe",
                ),
            )
        )

        composeTestRule.setContent {

            NavDisplay(
                backStack = backStack,
                entryDecorators = listOf(
                ),
                entryProvider = { key ->
                    when (key) {
                        is Route.CountriesList -> {
                            NavEntry(key) {
                                CountriesListContent(
                                    onCountryClick = {
                                        backStack.add(Route.CountryDetails(it))
                                    },
                                    onRefresh = {},
                                    state = state
                                )
                            }
                        }
                        is Route.CountryDetails -> {
                            NavEntry(key) {
                                Text("Details for ${key.name}")
                            }
                        }
                    }
                }
            )
        }

        // Находим страну в списке и кликаем
        composeTestRule.onNodeWithText("France").performClick()

        // Проверяем, что в стэк добавился новый экран
        assert(backStack.size == 2)
        assert(backStack.last() is Route.CountryDetails)

        // Проверяем результат
        composeTestRule.onNodeWithText("Details for France").assertIsDisplayed()
    }
}
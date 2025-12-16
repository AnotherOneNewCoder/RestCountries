package com.zhogin.restcountries.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zhogin.restcountries.ui.CountryDetailsViewModel
import com.zhogin.restcountries.ui.components.CountryItem

@Composable
fun CountryDetailsScreen(
    modifier: Modifier = Modifier,
    name: String,
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.loadCountryFromDb(name)
    }
    val state = viewModel.state
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isLoading && state.country == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (state.showErrorText) {
            Text(
                text = state.error ?: "Данные не найдены. Проверьте подключение.",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp)
            )
        } else {
            state.country?.let {
                CountryItem(
                    county = it
                )
            }

        }
    }
    if (state.error != null && state.country != null) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(
                    onClick = { viewModel.loadCountryFromDb(name) }
                ) {
                    Text("Повторить")
                }
            }
        ) {
            Text(
                text = state.error
            )
        }
    }

}
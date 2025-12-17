package com.zhogin.restcountries.ui.country.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zhogin.restcountries.R
import com.zhogin.restcountries.ui.country.CountryDetailsState
import com.zhogin.restcountries.ui.country.CountryDetailsViewModel
import com.zhogin.restcountries.ui.country.component.CountryItem

@Composable
fun CountryDetailsScreen(
    name: String,
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    // Загрузка данных при входе на экран
    LaunchedEffect(name) {
        viewModel.loadCountryFromDb(name)
    }

    CountryDetailsContent(
        state = viewModel.state,
        onRetry = { viewModel.loadCountryFromDb(name) }
    )
}

@Composable
fun CountryDetailsContent(
    state: CountryDetailsState,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading && state.country == null) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("loader_details")
            )
        } else if (state.showErrorText) {
            Text(
                text = state.error ?: stringResource(R.string.no_data_check_connection),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
            )
        } else {
            state.country?.let { CountryItem(county = it) }
        }
    }

    if (state.error != null && state.country != null) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = { TextButton(onClick = onRetry) { Text(stringResource(R.string.retry)) } }
        ) { Text(text = state.error) }
    }
}
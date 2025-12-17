package com.zhogin.restcountries.ui.countries.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zhogin.restcountries.R
import com.zhogin.restcountries.ui.countries.CountriesViewModel
import com.zhogin.restcountries.ui.countries.component.CountryListItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesListScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    onCountryClick: (String) -> Unit,
) {
    val state = viewModel.state
    val isRefreshing = state.isLoading && state.countries.isNotEmpty()
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
        ,
        isRefreshing = isRefreshing,
        onRefresh = {
            viewModel.loadCountries(forceRefresh = true)
        },
        state = pullToRefreshState
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.isLoading && state.countries.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.showErrorText) {
                Text(
                    text = state.error ?: stringResource(R.string.no_data_check_connection),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 24.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(items = state.countries, key = { it.name }) { country ->
                        CountryListItem(
                            county = country,
                            onClick = {
                                onCountryClick(it)
                            }
                        )
                    }
                }
            }
        }
    }
    if (state.error != null && state.countries.isNotEmpty()) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(
                    onClick = { viewModel.loadCountries(forceRefresh = true) }
                ) {
                    Text(stringResource(R.string.retry))
                }
            }
        ) {
            Text(
                text = state.error
            )
        }
    }
}
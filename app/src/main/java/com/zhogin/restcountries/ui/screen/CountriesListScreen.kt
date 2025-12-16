package com.zhogin.restcountries.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhogin.restcountries.R
import com.zhogin.restcountries.ui.CountriesViewModel
import com.zhogin.restcountries.ui.components.CountryListItem
import com.zhogin.restcountries.ui.theme.FirstGradient


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesListScreen(
    viewModel: CountriesViewModel
) {
    val state = viewModel.state

    val isRefreshing = state.isLoading && state.countries.isNotEmpty()
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Countries",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "menu"
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(FirstGradient)
            .paint(
                painter = painterResource(R.drawable.world_map),
                contentScale = ContentScale.FillBounds,
                alpha = 0.8f
            )


    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        text = state.error ?: "Данные не найдены. Проверьте подключение.",
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
                                onClick = { }
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


}


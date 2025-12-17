package com.zhogin.restcountries.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.zhogin.restcountries.R
import com.zhogin.restcountries.ui.countries.screen.CountriesListScreen
import com.zhogin.restcountries.ui.country.screen.CountryDetailsScreen
import com.zhogin.restcountries.ui.theme.FirstGradient
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.CountriesList::class, Route.CountriesList.serializer())
                    subclass(Route.CountryDetails::class, Route.CountryDetails.serializer())
                }
            }
        },
        Route.CountriesList
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (backStack.size == 1) {
                        Text(
                            text = stringResource(R.string.countries),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                navigationIcon = {
                    if (backStack.size > 1) {
                        IconButton(
                            onClick = { backStack.removeLast() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    }

                },
                actions = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent,
        modifier = modifier
            .fillMaxSize()
            .background(FirstGradient)
            .paint(
                painter = painterResource(R.drawable.world_map),
                contentScale = ContentScale.FillBounds,
                alpha = 0.8f
            )


    ) { paddingValues ->

        NavDisplay(
            modifier = Modifier
                .padding(paddingValues),
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = { key ->
                when (key) {
                    is Route.CountriesList -> {
                        NavEntry(key) {
                            CountriesListScreen(
                                onCountryClick = {
                                    backStack.add(Route.CountryDetails(it))
                                }
                            )
                        }
                    }

                    is Route.CountryDetails -> {
                        NavEntry(key) {
                            CountryDetailsScreen(
                                name = key.name
                            )
                        }
                    }

                    else -> error("Unknown NavKey: $key")
                }
            }
        )
    }
}
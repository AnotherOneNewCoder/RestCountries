package com.zhogin.restcountries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zhogin.restcountries.ui.CountriesViewModel
import com.zhogin.restcountries.ui.screen.CountriesListScreen
import com.zhogin.restcountries.ui.theme.RestCountriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestCountriesTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
                val viewModel = hiltViewModel<CountriesViewModel>()
                CountriesListScreen(viewModel)
            }
        }
    }
}


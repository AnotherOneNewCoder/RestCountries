package com.zhogin.restcountries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zhogin.restcountries.ui.navigation.NavigationRoot
import com.zhogin.restcountries.ui.theme.RestCountriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestCountriesTheme {
                NavigationRoot()
            }
        }
    }
}


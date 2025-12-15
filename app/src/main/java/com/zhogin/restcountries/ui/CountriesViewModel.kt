package com.zhogin.restcountries.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
): ViewModel() {

    var state by mutableStateOf(CountriesState())
        private set

    init {
        loadCountries(forceRefresh = false)
    }

    fun loadCountries(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getCountriesUseCase(forceRefresh).collect { result ->
                when(result) {
                    is Resource.Error<*> -> state = state.copy(
                        countries = result.data ?: emptyList(),
                        isLoading = false,
                        error = result.message,
                        showErrorText = result.data.isNullOrEmpty()
                    )
                    is Resource.Loading<*> -> state = state.copy(
                        countries = result.data ?: emptyList(),
                        isLoading = result.data == null,
                        error = null,
                        showErrorText = false
                    )
                    is Resource.Success<*> -> state = state.copy(
                        countries = result.data ?: emptyList(),
                        isLoading = false,
                        error = null,
                        showErrorText = result.data.isNullOrEmpty()
                    )
                }
            }
        }
    }
}
package com.zhogin.restcountries.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.domain.usecase.GetCountyDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val getCountyDetailsUseCase: GetCountyDetailsUseCase,

): ViewModel() {
    var state by mutableStateOf(CountryDetailsState())

    fun loadCountryFromDb(name: String) {
        viewModelScope.launch {
            when(val country = getCountyDetailsUseCase.invoke(name)) {
                is Resource.Error<*> -> state = state.copy(
                isLoading = false,
                error = country.message,
                showErrorText = country.data == null,
                country = country.data
            )
                is Resource.Loading<*> -> state = state.copy(
                    isLoading = true,
                    error = null,
                    showErrorText = false,
                    country = country.data
                )
                is Resource.Success<*> -> state = state.copy(
                    isLoading = false,
                    error = null,
                    showErrorText = country.data == null,
                    country = country.data
                )
            }
        }
    }
}
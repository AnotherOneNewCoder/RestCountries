package com.zhogin.restcountries.domain.usecase

import com.zhogin.restcountries.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountyDetailsUseCase@Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(name: String) = repository.getCountryDetails(name)
}
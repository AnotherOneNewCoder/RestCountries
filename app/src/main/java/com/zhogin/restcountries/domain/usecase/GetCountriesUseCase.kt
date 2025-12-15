package com.zhogin.restcountries.domain.usecase

import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.domain.repository.CountryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<Resource<List<Country>>> {
        return repository.getCountries(forceRefresh)
    }
}
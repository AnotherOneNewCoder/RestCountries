package com.zhogin.restcountries.data.repository

import com.zhogin.restcountries.common.IoDispatcher
import com.zhogin.restcountries.common.Resource
import com.zhogin.restcountries.data.api.service.CountriesApiService
import com.zhogin.restcountries.data.api.mapper.CountryDtoMapper
import com.zhogin.restcountries.data.cache.CountriesDatabase
import com.zhogin.restcountries.data.cache.mapper.CountryEntityMapper
import com.zhogin.restcountries.domain.model.Country
import com.zhogin.restcountries.domain.repository.CountryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


// Load from cache -> Load from network -> Refresh cache

@Suppress("UNCHECKED_CAST")
class CountryRepositoryImpl @Inject constructor(
    private val api: CountriesApiService,
    private val database: CountriesDatabase,
    private val dtoMapper: CountryDtoMapper,
    private val entityMapper: CountryEntityMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): CountryRepository {
    override fun getCountries(forceRefresh: Boolean): Flow<Resource<List<Country>>> {
        return flow {
            val cacheEntities = database.countryDao.getAllCountries().first()
            val hasCache = cacheEntities.isNotEmpty()
            if (hasCache) {
                emit(Resource.Loading(entityMapper.mapListToDomain(cacheEntities)))
            } else {
                emit(Resource.Loading(null))
            }
            try {
                val remoteDtos = api.getAllCountries()
                val entitiesToCache = dtoMapper.mapListToEntity(remoteDtos)
                database.countryDao.deleteAllCountries()
                database.countryDao.insertCountries(entitiesToCache)
                val freshCache = database.countryDao.getAllCountries().first()
                emit(Resource.Success(entityMapper.mapListToDomain(freshCache)))
            } catch (e: Exception) {
                if (!hasCache) {
                    emit(Resource.Error("Check internet connection", data = null))
                } else {
                    emit(Resource.Error("Update error: ${e.message}", entityMapper.mapListToDomain(cacheEntities)))
                }
            }
        }.flowOn(ioDispatcher) as Flow<Resource<List<Country>>>



    }

    override suspend fun getCountryDetails(name: String): Resource<Country> {
        return try {
            val entity = database.countryDao.getCountryByName(name)
            if (entity != null) {
                Resource.Success(entityMapper.mapToDomain(entity))
            } else {
                Resource.Error("Country not found in cache", null)
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error fetching data", null)
        }
    }
}
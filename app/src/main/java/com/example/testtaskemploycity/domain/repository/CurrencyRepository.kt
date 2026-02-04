package com.example.testtaskemploycity.domain.repository

import com.example.testtaskemploycity.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getLatestRates(baseCurrency: String, symbols: String): Result<List<CurrencyRate>>
    fun getFavorites(): Flow<List<CurrencyRate>>
    suspend fun addToFavorites(currencyCode: String, baseCurrency: String, rate: Double)
    suspend fun removeFromFavorites(currencyCode: String, baseCurrency: String)
    suspend fun isFavorite(currencyCode: String, baseCurrency: String): Boolean
}

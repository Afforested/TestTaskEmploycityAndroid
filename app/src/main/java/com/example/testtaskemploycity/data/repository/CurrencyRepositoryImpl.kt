package com.example.testtaskemploycity.data.repository

import com.example.testtaskemploycity.data.local.dao.FavoriteDao
import com.example.testtaskemploycity.data.local.entity.FavoriteCurrencyEntity
import com.example.testtaskemploycity.data.remote.api.ExchangeRatesApi
import com.example.testtaskemploycity.domain.model.Currency
import com.example.testtaskemploycity.domain.model.CurrencyNames
import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CurrencyRepositoryImpl @Inject constructor(
    private val exchangeRatesApi: ExchangeRatesApi,
    private val favoriteDao: FavoriteDao
) : CurrencyRepository {


    override suspend fun getLatestRates(baseCurrency: String, symbols: String): Result<List<CurrencyRate>> {
        return try {
            val response = exchangeRatesApi.getLatestRates(
                base = baseCurrency,
                symbols = symbols
            )

            if (!response.success) {
                return Result.failure(Exception("API request failed"))
            }

            val rates = response.rates.map { (code, rate) ->
                val isFav = favoriteDao.isFavorite(code, baseCurrency)
                CurrencyRate(
                    currency = Currency(
                        code = code,
                        name = CurrencyNames.names[code] ?: code
                    ),
                    rate = rate,
                    isFavorite = isFav
                )
            }

            Result.success(rates)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFavorites(): Flow<List<CurrencyRate>> {
        return favoriteDao.getAllFavorites().map { entities ->
            entities.map { entity ->
                CurrencyRate(
                    currency = Currency(
                        code = entity.currencyCode,
                        name = entity.currencyName
                    ),
                    rate = entity.rate,
                    isFavorite = true,
                    baseCurrency = entity.baseCurrency
                )
            }
        }
    }

    override suspend fun addToFavorites(currencyCode: String, baseCurrency: String, rate: Double) {
        val entity = FavoriteCurrencyEntity(
            id = "${currencyCode}_$baseCurrency",
            currencyCode = currencyCode,
            currencyName = CurrencyNames.names[currencyCode] ?: currencyCode,
            baseCurrency = baseCurrency,
            rate = rate
        )
        favoriteDao.addToFavorites(entity)
    }

    override suspend fun removeFromFavorites(currencyCode: String, baseCurrency: String) {
        favoriteDao.deleteByCodeAndBase(currencyCode, baseCurrency)
    }

    override suspend fun isFavorite(currencyCode: String, baseCurrency: String): Boolean {
        return favoriteDao.isFavorite(currencyCode, baseCurrency)
    }


}

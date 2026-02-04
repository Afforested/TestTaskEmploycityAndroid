package com.example.testtaskemploycity.domain.usecase

import com.example.testtaskemploycity.domain.repository.CurrencyRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(currencyCode: String, baseCurrency: String, rate: Double) {
        repository.addToFavorites(currencyCode, baseCurrency, rate)
    }
}

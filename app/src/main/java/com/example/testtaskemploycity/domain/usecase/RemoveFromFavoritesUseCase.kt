package com.example.testtaskemploycity.domain.usecase

import com.example.testtaskemploycity.domain.repository.CurrencyRepository
import javax.inject.Inject


class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(currencyCode: String, baseCurrency: String) {
        repository.removeFromFavorites(currencyCode, baseCurrency)
    }
}

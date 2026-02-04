package com.example.testtaskemploycity.domain.usecase

import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteCurrenciesUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    operator fun invoke(): Flow<List<CurrencyRate>> {
        return repository.getFavorites()
    }
}

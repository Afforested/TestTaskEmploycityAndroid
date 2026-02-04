package com.example.testtaskemploycity.domain.usecase

import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.model.SortType
import com.example.testtaskemploycity.domain.repository.CurrencyRepository
import javax.inject.Inject


class GetLatestRatesUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val sortCurrencyRatesUseCase: SortCurrencyRatesUseCase
) {
    suspend operator fun invoke(
        baseCurrency: String,
        sortType: SortType = SortType.ALPHABETIC_ASC
    ): Result<List<CurrencyRate>> {
        return repository.getLatestRates(baseCurrency, SELECTED_CURRENCIES).map { rates ->
            sortCurrencyRatesUseCase(rates, sortType)
        }
    }

    companion object {
        private const val SELECTED_CURRENCIES = "AED,USD,BYN,RUB"
    }
}

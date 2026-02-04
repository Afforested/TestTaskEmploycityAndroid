package com.example.testtaskemploycity.domain.usecase

import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.model.SortType
import javax.inject.Inject

class SortCurrencyRatesUseCase @Inject constructor() {
    operator fun invoke(rates: List<CurrencyRate>, sortType: SortType): List<CurrencyRate> {
        return when (sortType) {
            SortType.ALPHABETIC_ASC -> rates.sortedBy { it.currency.code }
            SortType.ALPHABETIC_DESC -> rates.sortedByDescending { it.currency.code }
            SortType.RATE_ASC -> rates.sortedBy { it.rate }
            SortType.RATE_DESC -> rates.sortedByDescending { it.rate }
        }
    }
}

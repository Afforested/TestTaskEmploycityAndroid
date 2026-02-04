package com.example.testtaskemploycity.presentation.rates.viewmodel

import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.model.SortType

data class RatesState(
    val rates: List<CurrencyRate> = emptyList(),
    val baseCurrency: String = "USD",
    val sortType: SortType = SortType.ALPHABETIC_ASC,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)
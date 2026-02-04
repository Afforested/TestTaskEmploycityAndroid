package com.example.testtaskemploycity.presentation.rates.viewmodel

import com.example.testtaskemploycity.domain.model.CurrencyRate
import com.example.testtaskemploycity.domain.model.SortType


sealed class RatesEvent {
    data class ChangeBaseCurrency(val currency: String) : RatesEvent()
    data class ChangeSortType(val sortType: SortType) : RatesEvent()
    data class ToggleFavorite(val currencyRate: CurrencyRate) : RatesEvent()
    object Refresh : RatesEvent()
}
package com.example.testtaskemploycity.presentation.favorites.viewmodel

import com.example.testtaskemploycity.domain.model.CurrencyRate

sealed class FavoritesEvent {
    data class RemoveFavorite(val currencyRate: CurrencyRate) : FavoritesEvent()
}

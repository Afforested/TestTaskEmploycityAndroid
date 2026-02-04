package com.example.testtaskemploycity.presentation.favorites.viewmodel

import com.example.testtaskemploycity.domain.model.CurrencyRate

data class FavoritesState(
    val favorites: List<CurrencyRate> = emptyList()
)

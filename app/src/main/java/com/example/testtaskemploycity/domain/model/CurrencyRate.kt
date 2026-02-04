package com.example.testtaskemploycity.domain.model

data class CurrencyRate(
    val currency: Currency,
    val rate: Double,
    val isFavorite: Boolean = false,
    val baseCurrency: String? = null
)
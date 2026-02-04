package com.example.testtaskemploycity.domain.model


fun CurrencyRate.getDisplayPair(): String {
    return if (baseCurrency != null) {
        "$baseCurrency/${currency.code}"
    } else {
        currency.code
    }
}

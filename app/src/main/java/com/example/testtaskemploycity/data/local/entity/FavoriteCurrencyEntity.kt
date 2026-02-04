package com.example.testtaskemploycity.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_currencies")
data class FavoriteCurrencyEntity(
    @PrimaryKey
    val id: String,
    val currencyCode: String,
    val currencyName: String,
    val baseCurrency: String,
    val rate: Double,
    val timestamp: Long = System.currentTimeMillis()
)

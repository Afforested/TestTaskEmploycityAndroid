package com.example.testtaskemploycity.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testtaskemploycity.data.local.entity.FavoriteCurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_currencies")
    fun getAllFavorites(): Flow<List<FavoriteCurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(entity: FavoriteCurrencyEntity)

    @Delete
    suspend fun removeFromFavorites(entity: FavoriteCurrencyEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_currencies WHERE currencyCode = :currencyCode AND baseCurrency = :baseCurrency)")
    suspend fun isFavorite(currencyCode: String, baseCurrency: String): Boolean

    @Query("DELETE FROM favorite_currencies WHERE currencyCode = :currencyCode AND baseCurrency = :baseCurrency")
    suspend fun deleteByCodeAndBase(currencyCode: String, baseCurrency: String)
}

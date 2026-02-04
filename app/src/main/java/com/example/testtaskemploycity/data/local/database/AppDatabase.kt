package com.example.testtaskemploycity.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testtaskemploycity.data.local.dao.FavoriteDao
import com.example.testtaskemploycity.data.local.entity.FavoriteCurrencyEntity

@Database(
    entities = [FavoriteCurrencyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

package com.example.shopique.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        FavoriteEntity::class
    ],
    version = 3
)
abstract class ShopiqueDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun cartDao(): CartDao

    abstract fun favoriteDao(): FavoriteDao
}
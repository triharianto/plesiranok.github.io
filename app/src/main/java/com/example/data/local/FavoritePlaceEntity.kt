package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class FavoritePlaceEntity(
    @PrimaryKey
    val placeId: String,
    val regionId: String,
    val categoryId: String,
    val name: String,
    val address: String,
    val rating: Double,
    val priceTag: String,
    val contactNumber: String,
    val savedAtTimestamp: Long = System.currentTimeMillis()
)

package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePlaceDao {
    @Query("SELECT * FROM favorite_places ORDER BY savedAtTimestamp DESC")
    fun getAllFavorites(): Flow<List<FavoritePlaceEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_places WHERE placeId = :placeId)")
    fun isFavorite(placeId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoritePlaceEntity)

    @Query("DELETE FROM favorite_places WHERE placeId = :placeId")
    suspend fun deleteFavoriteById(placeId: String)
}

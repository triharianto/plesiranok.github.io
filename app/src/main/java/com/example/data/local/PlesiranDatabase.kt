package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritePlaceEntity::class], version = 1, exportSchema = false)
abstract class PlesiranDatabase : RoomDatabase() {
    abstract fun favoritePlaceDao(): FavoritePlaceDao

    companion object {
        @Volatile
        private var INSTANCE: PlesiranDatabase? = null

        fun getInstance(context: Context): PlesiranDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlesiranDatabase::class.java,
                    "plesiran_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

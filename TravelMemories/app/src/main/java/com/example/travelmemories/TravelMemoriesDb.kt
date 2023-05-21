package com.example.travelmemories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoryEntity::class], version = 1)
abstract class TravelMemoriesDb : RoomDatabase() {
    abstract fun memoriesDAO(): MemoriesDAO

    companion object {
        @Volatile
        private var INSTANCE: TravelMemoriesDb? = null

        fun getInstances(context: Context): TravelMemoriesDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TravelMemoriesDb::class.java,
                    "travel_memories_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ INSTANCE = it }
            }
        }
    }
}
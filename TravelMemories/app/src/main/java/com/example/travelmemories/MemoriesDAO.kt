package com.example.travelmemories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemoriesDAO {

    @Query("SELECT * FROM memory_table")
    suspend fun getAll():List<MemoryEntity>

    @Query("SELECT * FROM memory_table WHERE id= :id")
    suspend fun get(id: Int): MemoryEntity

    @Insert
    suspend fun insert(memory: MemoryEntity)

    @Delete
    suspend fun delete(memory: MemoryEntity)

    @Query("DELETE FROM memory_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(memory: MemoryEntity)
}
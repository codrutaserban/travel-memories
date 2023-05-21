package com.example.travelmemories

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_table")
data class MemoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val date: String,
    val type: String,
    val mood: String,
    val notes: String,
    val image: String,
)
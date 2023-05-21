package com.example.travelmemories

data class Memory(val id: Int, val name: String, val location: String, val image: String, val date: String) {
    companion object {
        fun fromEntity(entity: MemoryEntity): Memory {
            return Memory(entity.id, entity.name,entity.location, entity.image ,entity.date)
        }
    }
}

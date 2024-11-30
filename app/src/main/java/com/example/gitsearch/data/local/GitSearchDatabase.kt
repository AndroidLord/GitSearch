package com.example.gitsearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gitsearch.data.local.converters.OwnerTypeConvertor
import com.example.gitsearch.data.local.entity.RepositoryEntity

@Database(
    entities = [
        RepositoryEntity::class,
    ], version = 1
)
@TypeConverters(OwnerTypeConvertor::class)
abstract class GitSearchDatabase() : RoomDatabase() {
    abstract fun gitSearchDao(): GitSearchDao
}

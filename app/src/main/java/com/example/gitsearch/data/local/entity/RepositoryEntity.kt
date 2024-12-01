package com.example.gitsearch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val query: String,
    val fullName: String,
    val description: String?,
    val stargazersCount: Int,
    val forksCount: Int,
    val owner: OwnerEntity
)

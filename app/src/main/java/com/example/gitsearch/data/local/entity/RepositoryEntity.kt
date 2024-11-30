package com.example.gitsearch.data.local.entity

import androidx.room.Entity

@Entity
data class RepositoryEntity(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val stargazersCount: Int,
    val forksCount: Int,
    val owner: OwnerEntity
)

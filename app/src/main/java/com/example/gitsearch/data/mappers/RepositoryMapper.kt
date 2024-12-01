package com.example.gitsearch.data.mappers

import com.example.gitsearch.data.local.entity.OwnerEntity
import com.example.gitsearch.data.local.entity.RepositoryEntity
import com.example.gitsearch.data.remote.dto.OwnerDTO
import com.example.gitsearch.data.remote.dto.RepositoryDTO

fun OwnerDTO.toOwnerEntity(): OwnerEntity {
    return OwnerEntity(
        username = username,
        avatarUrl = avatarUrl
    )
}

fun RepositoryDTO.toRepositoryEntity(query: String): RepositoryEntity {
    return RepositoryEntity(
        id = id,
        name = name,
        query = query.lowercase(),
        fullName = fullName,
        description = description,
        stargazersCount = stargazersCount,
        forksCount = forksCount,
        owner = owner.toOwnerEntity()
    )
}


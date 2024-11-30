package com.example.gitsearch.data.local.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class OwnerEntity(
    val username: String,
    val avatarUrl: String
)


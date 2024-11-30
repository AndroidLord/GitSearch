package com.example.gitsearch.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ContributorDTO(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("contributions") val contributions: Int
)

package com.example.gitsearch.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepositoryDetailsDTO(
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("open_issues_count") val openIssuesCount: Int,
    @SerializedName("owner") val owner: OwnerDTO
)

package com.example.gitsearch.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RepoSearchResponseDTO(
    @SerializedName("items") val repositories: List<RepositoryDTO>
)

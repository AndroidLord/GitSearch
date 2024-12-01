package com.example.gitsearch.data.remote.api

import com.example.gitsearch.data.remote.dto.ContributorDTO
import com.example.gitsearch.data.remote.dto.RepoSearchResponseDTO
import com.example.gitsearch.data.remote.dto.RepositoryDTO
import com.example.gitsearch.data.remote.dto.RepositoryDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): RepoSearchResponseDTO

    @GET("repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Long = 10
    ): RepositoryDetailsDTO

    @GET("repos/{owner}/{repo}/contributors")
    suspend fun getContributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Long = 10
    ): List<ContributorDTO>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("page") page: Long,
        @Query("per_page") perPage: Long = 10
    ): List<RepositoryDTO>

}
package com.example.gitsearch.di

import android.content.Context
import androidx.room.Room
import com.example.gitsearch.core.Utils
import com.example.gitsearch.data.local.GitSearchDatabase
import com.example.gitsearch.data.remote.api.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApiService(): GithubApiService {
        val githubApiService by lazy {
            Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubApiService::class.java)
        }
        return githubApiService
    }

    @Provides
    @Singleton
    fun provideGitSearchDatabase(
        @ApplicationContext context: Context
    ): GitSearchDatabase {
        return Room.databaseBuilder(
            context,
            GitSearchDatabase::class.java,
            "git_search_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGitSearchDao(
        database: GitSearchDatabase
    ) = database.gitSearchDao()

    @Provides
    @Singleton
    fun provideContext(
        @ApplicationContext context: Context
    ) = context


}
package com.example.gitsearch.ui.screen.homedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.data.GitSearchRepository
import com.example.gitsearch.data.remote.dto.ContributorDTO
import com.example.gitsearch.data.remote.dto.RepositoryDetailsDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GitSearchRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        val owner = savedStateHandle.get<String>("owner")
            ?: throw IllegalArgumentException("Owner is required")
        val repo = savedStateHandle.get<String>("repo")
            ?: throw IllegalArgumentException("Repo is required")

        fetchRepoDetails(owner, repo)
    }

    private fun fetchRepoDetails(owner: String, repo: String) {
        viewModelScope.launch {
            try {

                val repoDetailsDeferred = async { repository.getGitRepoDetails(owner, repo) }
                val contributorsDeferred = async { repository.getContributors(owner, repo) }

                val repoDetails = repoDetailsDeferred.await()
                val contributors = contributorsDeferred.await()

                _uiState.value = DetailUiState.Success(
                    repoDetails,
                    contributors
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(
                    e.localizedMessage ?: "Failed to load repository details"
                )
            }
        }
    }

    fun retry() {
        val owner = savedStateHandle.get<String>("owner")
        val repo = savedStateHandle.get<String>("repo")

        if (owner != null && repo != null) {
            fetchRepoDetails(owner, repo)
        }
    }
}

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Success(
        val repoDetails: RepositoryDetailsDTO,
        val contributors: List<ContributorDTO>
    ) : DetailUiState()

    data class Error(val message: String) : DetailUiState()
}
package com.example.gitsearch.ui.screen.home


import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitsearch.data.GitSearchRepository
import com.example.gitsearch.data.local.entity.RepositoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GitSearchRepository
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
        private const val QUERY_KEY = "QUERY_KEY"
        private const val DEFAULT_QUERY = ""
    }

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val searchQuery = savedStateHandle.getStateFlow(QUERY_KEY, DEFAULT_QUERY)

    fun onQueryChanged(query: String) {
        savedStateHandle[QUERY_KEY] = query
    }

    fun onSearchQueryClick() {
        try {
            _uiState.update { HomeUiState.Loading }

            val data = repository.getGitRepoSearchPagingSource(searchQuery.value)
                .cachedIn(viewModelScope)

            _uiState.update { HomeUiState.Success(data) }


        } catch (e: Exception) {
            Log.d(TAG, "getGitRepoSearchPagingSource: ${e.message}")
            _uiState.value = HomeUiState.Error("Something went wrong!")
        }
    }


}

sealed class HomeUiState {
    data object Idle : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val data: Flow<PagingData<RepositoryEntity>>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}


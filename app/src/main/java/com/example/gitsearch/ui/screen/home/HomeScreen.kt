package com.example.gitsearch.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitsearch.core.NetworkUtils
import com.example.gitsearch.data.local.entity.RepositoryEntity
import com.example.gitsearch.ui.navigation.ScreenRoute

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()

    val isNetworkAvailable = remember {
        NetworkUtils.isNetworkAvailable(context)
    }

    Scaffold(
        topBar = {
            Text(
                text = "GitSearch",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )
        }
    ) {
        HomeContent(
            modifier = modifier.padding(it),
            uiState = uiState,
            searchQuery = searchQuery.value,
            onQueryChanged = viewModel::onQueryChanged,
            onSearchClick = viewModel::onSearchQueryClick,
            onGitRepoClick = { owner: String, repo: String ->
                if (!isNetworkAvailable) {
                   Toast.makeText(context, "No network available", Toast.LENGTH_SHORT).show()
                    return@HomeContent
                }
                navController.navigate(
                    ScreenRoute.Detail(
                        owner = owner,
                        repo = repo
                    )
                )
            }
        )
    }
}


@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit,
    onGitRepoClick: (owner: String, repo: String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SearchBar(
            searchQuery = searchQuery,
            onQueryChanged = onQueryChanged,
            onSearchClick = onSearchClick
        )

        when (uiState) {
            is HomeUiState.Idle -> {
                Text(
                    text = "Enter a query to search.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is HomeUiState.Success -> {
                PagingList(
                    gitSearchRepo = uiState.data.collectAsLazyPagingItems(),
                    onGitRepoClick = onGitRepoClick
                )
            }

            is HomeUiState.Error -> {
                Text(
                    text = "Error: ${uiState.message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


@Composable
private fun SearchBar(
    searchQuery: String,
    onQueryChanged: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onQueryChanged,
            label = { Text("Search") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClick() }
            )
        )
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Composable
private fun PagingList(
    gitSearchRepo: LazyPagingItems<RepositoryEntity>,
    onGitRepoClick: (owner: String, repo: String) -> Unit
) {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        when (val refreshState = gitSearchRepo.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is LoadState.Error -> {
                Text(
                    text = "Error: ${refreshState.error.message ?: "Unknown error"}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        count = gitSearchRepo.itemCount,
                        key = { index -> gitSearchRepo[index]?.id ?: index },
                        contentType = { "repository" }
                    ) { index ->
                        gitSearchRepo[index]?.let { repo ->
                            RepoItem(
                                repo = repo,
                                onGitRepoClick = onGitRepoClick
                            )
                        }
                    }

                    item {
                        if (gitSearchRepo.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepoItem(
    repo: RepositoryEntity,
    onGitRepoClick: (owner: String, repo: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onGitRepoClick(repo.owner.username, repo.name) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = repo.name, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = repo.description ?: "No description",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "⭐ ${repo.stargazersCount} | 🍴 ${repo.forksCount}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

package com.example.gitsearch.ui.screen.homedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.ContributorDTO
import com.example.gitsearch.data.remote.dto.RepositoryDetailsDTO
import com.example.gitsearch.ui.navigation.ScreenRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repository Details") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is DetailUiState.Success -> {
                    RepositoryDetailsContent(
                        repoDetails = state.repoDetails,
                        contributors = state.contributors,
                        onProjectLinkClick = {
                            navController.navigate(ScreenRoute.WebView(url = it))
                        }
                    )
                }

                is DetailUiState.Error -> {
                    ErrorContent(
                        errorMessage = state.message,
                        onRetry = { viewModel.retry() }
                    )
                }
            }
        }
    }
}

@Composable
private fun RepositoryDetailsContent(
    repoDetails: RepositoryDetailsDTO,
    contributors: List<ContributorDTO>,
    onProjectLinkClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Repo Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = repoDetails.owner.avatarUrl,
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = "Repository Owner Avatar",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = repoDetails.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = repoDetails.fullName,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Description
        item {
            Spacer(modifier = Modifier.height(16.dp))
            repoDetails.description?.let { description ->
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // Project Link
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProjectLinkClick(repoDetails.htmlUrl) }
                    .padding(vertical = 8.dp)
            ) {
                AsyncImage(
                    model = repoDetails.owner.avatarUrl,
                    contentDescription = "Repository Owner Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Project Link",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Stats
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Stars", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = repoDetails.stargazersCount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column {
                    Text("Forks", style = MaterialTheme.typography.labelMedium)
                    Text(
                        text = repoDetails.forksCount.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                repoDetails.language?.let { language ->
                    Column {
                        Text("Language", style = MaterialTheme.typography.labelMedium)
                        Text(
                            text = language,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Contributors
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Contributors",
                style = MaterialTheme.typography.titleMedium
            )
        }

        items(contributors.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = contributors[index].avatarUrl,
                    contentDescription = "Contributor Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = contributors[index].username,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${contributors[index].contributions} contributions",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}


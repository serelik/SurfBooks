package com.serelik.surfbooks.ui.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serelik.surfbooks.R
import com.serelik.surfbooks.ui.common.BookItemUi
import com.serelik.surfbooks.ui.favorite.BookItemUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksListScreen(
    viewModel: BookSearchViewModel = hiltViewModel(),
    onItemClick: (id: String) -> Unit
) {
    val uiState = viewModel.booksStateFlow.collectAsStateWithLifecycle()
    val queryState = viewModel.searchQuery.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = queryState.value,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = {
                        Toast.makeText(context, "OnSearchCall", Toast.LENGTH_SHORT).show()
                    },
                    expanded = false,
                    onExpandedChange = { },
                    leadingIcon = {
                        Icon(
                            ImageVector.vectorResource(R.drawable.search_icon),
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    },
                    trailingIcon = {
                        if (queryState.value.isNotBlank()) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear",
                                modifier = Modifier.clickable {
                                    viewModel.onQueryChange("")
                                })
                        }
                    },
                )
            },
            expanded = false,
            onExpandedChange = { },
            content = { },
        )

        UiStateHandler(
            uiState.value,
            onItemClick,
            viewModel::onFavoriteClick,
            viewModel::retrySearch
        )
    }
}

@Composable
fun UiStateHandler(
    uiState: BookSearchUiState,
    onItemClick: (id: String) -> Unit,
    onFavoriteClick: (book: BookItemUiModel) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        BookSearchUiState.EmptyQuery -> EmptyQuery(stringResource(R.string.empty_query_message))
        BookSearchUiState.EmptyResult -> EmptyResult(stringResource(R.string.not_found))
        BookSearchUiState.Error -> ErrorSearch(
            stringResource(R.string.search_error),
            onRetryClick
        )

        BookSearchUiState.Loading -> Loader()
        is BookSearchUiState.Result -> SuccessResult(
            uiState.bookList, onItemClick, onFavoriteClick
        )
    }
}

@Composable
fun SuccessResult(
    books: List<BookItemUiModel>,
    onItemClick: (id: String) -> Unit,
    onFavoriteClick: (book: BookItemUiModel) -> Unit,
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(books) {
            BookItemUi(
                bookItemUiModel = it,
                onItemClick = onItemClick,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}

@Composable
fun EmptyQuery(message: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun EmptyResult(message: String) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun ErrorSearch(message: String, onRetryClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message, textAlign = TextAlign.Center, modifier = Modifier.padding(
                bottom = 12.dp,
                start = 20.dp,
                end = 20.dp
            )
        )
        Button(
            onClick = { onRetryClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = true,
            shape = Shapes().small,
            contentPadding = PaddingValues(
                vertical = 10.dp,
                horizontal = 22.dp
            ),
        ) { Text(stringResource(R.string.retry)) }
    }
}

@Composable
fun Loader() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
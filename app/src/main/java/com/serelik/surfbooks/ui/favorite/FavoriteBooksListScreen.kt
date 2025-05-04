package com.serelik.surfbooks.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serelik.surfbooks.R
import com.serelik.surfbooks.ui.common.BookItemUi

@Composable
fun FavoriteBooksListScreen(
    viewModel: FavoriteListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onItemClick: (id: String) -> Unit,
    onFavoriteClickSnackBar: (isFavorite: Boolean) -> Unit
) {

    val uiState = viewModel.favoriteBooksStateFlow.collectAsStateWithLifecycle(emptyList())
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable {
                        onBackClick()
                    }
            )

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(R.string.favorite_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        SuccessResult(
            books = uiState.value,
            onItemClick = onItemClick,
            onFavoriteClick = viewModel::onFavoriteClick,
            onFavoriteClickSnackBar = onFavoriteClickSnackBar,
        )
    }
}

@Composable
fun SuccessResult(
    books: List<BookItemUiModel>,
    onItemClick: (id: String) -> Unit,
    onFavoriteClick: (book: BookItemUiModel) -> Unit,
    onFavoriteClickSnackBar: (isFavorite: Boolean) -> Unit
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(books) {
            BookItemUi(
                it, onItemClick, onFavoriteClick,
                onFavoriteClickSnackBar = onFavoriteClickSnackBar
            )
        }
    }
}
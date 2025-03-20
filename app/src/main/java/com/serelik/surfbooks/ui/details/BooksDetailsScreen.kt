package com.serelik.surfbooks.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BooksDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: BookDetailsViewModel = hiltViewModel(),
) {
    val bookDetailsState = viewModel.bookDetailsStateFlow.collectAsStateWithLifecycle()
    val isBookFavoriteState = viewModel.bookFavoriteStateFlow.collectAsStateWithLifecycle()

    val bookState = bookDetailsState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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

            FavoriteIcon(
                bookState = bookState,
                isBookFavorite = isBookFavoriteState.value,
                onFavoriteClick = viewModel::onFavoriteClick
            )
        }

        UiStateHandler(bookState)
    }
}

@Composable
fun FavoriteIcon(
    bookState: BookDetailsUiState,
    isBookFavorite: Boolean,
    onFavoriteClick: (BookItem) -> Unit
) {
    if (bookState !is BookDetailsUiState.Result)
        return

    val color = if (isBookFavorite) Color.Red else Color.LightGray

    Icon(
        imageVector = Icons.Filled.Favorite,
        tint = color,
        contentDescription = "favorite",
        modifier = Modifier
            .shadow(shape = ShapeDefaults.ExtraLarge, elevation = 10.dp)
            .background(color = Color.White, shape = ShapeDefaults.ExtraLarge)
            .padding(6.dp)
            .clickable {
                onFavoriteClick(bookState.bookItem)
            }
    )
}

@Composable
fun SuccessResult(book: BookItem) {
    Card(modifier = Modifier.padding(horizontal = 80.dp, vertical = 12.dp)) {
        book.imageUrl?.let {
            GlideImage(
                it,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .aspectRatio(200.0f / 300.0f),
                contentDescription = null
            )
        }
    }

    Text(
        text = book.authors.joinToString(),
        style = Typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(),
        color = Color.LightGray,
        textAlign = TextAlign.Center
    )

    Text(
        text = book.title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        style = Typography.bodyLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )

    Text(
        text = "${book.publishedYear} г.",
        style = Typography.bodySmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 22.dp, top = 8.dp),
        color = Color.LightGray,
        textAlign = TextAlign.Center
    )
    Card {
        Text(
            text = "Описание",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 16.dp, top = 20.dp)
        )

        Text(
            text = book.description,
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun UiStateHandler(uiState: BookDetailsUiState) {
    when (uiState) {
        BookDetailsUiState.Error -> ErrorSearch("backend sent not all info")
        BookDetailsUiState.Loading -> Loader()
        is BookDetailsUiState.Result -> SuccessResult(uiState.bookItem)
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


@Composable
fun ErrorSearch(message: String) {
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
    }
}
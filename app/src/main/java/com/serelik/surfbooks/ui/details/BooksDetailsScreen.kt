package com.serelik.surfbooks.ui.details

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serelik.surfbooks.R
import com.serelik.surfbooks.domain.models.BookItem
import com.serelik.surfbooks.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BooksDetailsScreen(
    onBackClick: () -> Unit,
    viewModel: BookDetailsViewModel = hiltViewModel(),
    onFavoriteClickSnackBar: (isFavorite: Boolean) -> Unit
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

            if (bookState is BookDetailsUiState.Result) {

                val size: Dp by animateDpAsState(
                    if (isBookFavoriteState.value) 24.dp else 0.dp,
                )

                Box(
                    Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {

                            onFavoriteClickSnackBar(isBookFavoriteState.value)
                            viewModel.onFavoriteClick(bookState.bookItem)
                        }

                ) {

                    AnimatedIcon()

                    AnimatedIcon(size, Color.Red, Color.Transparent)


                }
            }
        }

        UiStateHandler(bookState)
    }
}

@Composable
fun BoxScope.AnimatedIcon(
    size: Dp = 24.dp,
    heartColor: Color = Color.LightGray,
    backgroundColor: Color = Color.White
) {
    Icon(
        imageVector = Icons.Filled.Favorite,
        tint = heartColor,
        contentDescription = stringResource(R.string.favorite_description),
        modifier = Modifier
            .align(Alignment.Center)
            .padding(top = 6.dp, end = 10.dp)
            .background(color = backgroundColor, shape = ShapeDefaults.ExtraLarge)
            .padding(6.dp)
            .size(size)

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

    if (!book.publishedYear.isNullOrBlank()) {
        Text(
            text = stringResource(R.string.year_template, book.publishedYear),
            style = Typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp, top = 8.dp),
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }

    if (book.description.isNotBlank()) {
        Card {
            Text(
                text = stringResource(R.string.description_title),
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
}

@Composable
fun UiStateHandler(uiState: BookDetailsUiState) {
    when (uiState) {
        BookDetailsUiState.Error -> ErrorSearch(stringResource(R.string.search_error))
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
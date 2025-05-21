package com.serelik.surfbooks.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serelik.surfbooks.R
import com.serelik.surfbooks.ui.favorite.BookItemUiModel
import com.serelik.surfbooks.ui.theme.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BookItemUi(
    bookItemUiModel: BookItemUiModel,
    onItemClick: (id: String) -> Unit,
    onFavoriteClick: (book: BookItemUiModel) -> Unit,
    onFavoriteClickSnackBar: (isFavorite: Boolean) -> Unit
) {

    val bookItem = bookItemUiModel.bookItem
    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 200.dp)
            .clickable {
                onItemClick(bookItem.id)
            }
    ) {
        Card() {
            Box() {
                bookItem.imageUrl?.let {
                    GlideImage(
                        it,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(154.0f / 230.0f),
                        contentDescription = null
                    )
                }

                val size: Dp by animateDpAsState(
                    if (bookItemUiModel.isFavorite) 24.dp else 0.dp
                )

                Box(
                    Modifier
                        .align(Alignment.TopEnd)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {

                            onFavoriteClickSnackBar(bookItemUiModel.isFavorite)
                            onFavoriteClick(bookItemUiModel)
                        }

                ) {

                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        tint = Color.LightGray,
                        contentDescription = stringResource(R.string.favorite_description),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 6.dp, end = 10.dp)
                            .background(color = Color.White, shape = ShapeDefaults.ExtraLarge)
                            .padding(6.dp)

                    )

                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        tint = Color.Red,
                        contentDescription = stringResource(R.string.favorite_description),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 6.dp, end = 10.dp)
                            .padding(6.dp)
                            .size(size)
                    )

                }
            }
        }

        Text(
            text = bookItem.authors.joinToString(),
            style = Typography.bodyLarge,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Text(
            bookItem.title,
            style = Typography.bodyLarge,
        )
    }
}
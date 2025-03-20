package com.serelik.surfbooks.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookItem(
    val id: String,
    val title: String,
    val authors: List<String>,
    val publishedYear: String?,
    val description: String,
    val imageUrl: String?
): Parcelable
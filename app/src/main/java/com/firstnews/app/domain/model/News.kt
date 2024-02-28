package com.firstnews.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class NewsType {
    HeadlineNews,
    Android,
    Indonesia,
    Samsung,
    Programming,
    Recommendation
}

enum class NewsCategory(val route: String) {
    None(""),
    Business("business"),
    Entertainment("entertainment"),
    General("general"),
    Health("health"),
    Science("science"),
    Sports("sports"),
    Technology("technology"),
}

@Parcelize
data class News(
    val id: Int = 0,
    val urlToImage: String? = null,
    val source: String = "",
    val title: String = "",
    val author: String = "",
    val url: String = "",
    val publishedAt: String = "",
    val saved: Boolean = false,
): Parcelable

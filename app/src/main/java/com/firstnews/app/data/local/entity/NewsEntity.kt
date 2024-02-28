package com.firstnews.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String = "",
    val urlToImage: String? = null,
    val title: String = "",
    val author: String = "",
    val publishedAt: String = "",
    val source: String = "",
    val type: NewsType = NewsType.HeadlineNews,
    val category: NewsCategory = NewsCategory.None
)

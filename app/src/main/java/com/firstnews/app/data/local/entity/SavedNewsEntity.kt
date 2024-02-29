package com.firstnews.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String = "",
    val urlToImage: String? = null,
    val title: String = "",
    val author: String = "",
    val publishedAt: String = "",
    val source: String = ""
)

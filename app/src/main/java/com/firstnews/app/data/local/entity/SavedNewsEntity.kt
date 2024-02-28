package com.firstnews.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedNewsEntity(
    @PrimaryKey
    val url: String = ""
)

package com.firstnews.app.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.firstnews.app.data.local.entity.SavedNewsEntity

@Dao
interface SavedNewsDao {

    @Query("SELECT * FROM savednewsentity")
    fun getNewsStream(): LiveData<List<SavedNewsEntity>>

    @Upsert
    suspend fun upsertNews(news: SavedNewsEntity)

    @Delete
    suspend fun deleteNews(news: SavedNewsEntity)

}
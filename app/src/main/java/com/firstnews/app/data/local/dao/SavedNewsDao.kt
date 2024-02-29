package com.firstnews.app.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.firstnews.app.data.local.entity.NewsEntity
import com.firstnews.app.data.local.entity.SavedNewsEntity

@Dao
interface SavedNewsDao {

    @Query("SELECT * FROM savednewsentity")
    fun getSavedNewsStream(): PagingSource<Int, SavedNewsEntity>

    @Query("SELECT * FROM savednewsentity")
    suspend fun getSavedNews(): List<SavedNewsEntity>

    @Query("SELECT EXISTS(SELECT * FROM savednewsentity WHERE url = :url)")
    fun isNewsSaved(url: String): LiveData<Boolean>

    @Upsert
    suspend fun upsertSavedNews(news: SavedNewsEntity)

    @Query("DELETE FROM savednewsentity WHERE url = :url")
    suspend fun deleteSavedNews(url: String)

}
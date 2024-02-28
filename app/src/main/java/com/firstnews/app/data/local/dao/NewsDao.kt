package com.firstnews.app.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.firstnews.app.data.local.entity.NewsEntity
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType

@Dao
interface NewsDao {

    @Query("SELECT * FROM newsentity")
    fun getNewsPagingSource(): PagingSource<Int, NewsEntity>

    @Query("SELECT * FROM newsentity WHERE type = :type")
    suspend fun getNews(type: NewsType): List<NewsEntity>

    @Query("SELECT * FROM newsentity WHERE type = :type")
    fun getNewsByType(type: NewsType): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM newsentity WHERE category = :category")
    fun getNewsByCategory(category: NewsCategory): LiveData<List<NewsEntity>>

    @Upsert
    suspend fun upsertNews(newsEntities: List<NewsEntity>)

    @Query("DELETE FROM newsentity WHERE type = :type")
    suspend fun clearNewsByType(type: NewsType)

    @Query("DELETE FROM newsentity WHERE category = :category")
    suspend fun clearNewsByCategory(category: NewsCategory)

}
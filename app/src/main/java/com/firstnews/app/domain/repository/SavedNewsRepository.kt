package com.firstnews.app.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.firstnews.app.domain.model.News

interface SavedNewsRepository {

    fun getSavedNewsStream(): LiveData<PagingData<News>>

    suspend fun getSavedNews(): List<News>

    fun isNewsSaved(url: String): LiveData<Boolean>

    suspend fun upsertSavedNews(news: News)

    suspend fun deleteSavedNews(news: News)

}
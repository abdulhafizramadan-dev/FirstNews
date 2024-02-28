package com.firstnews.app.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType
import com.firstnews.app.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getHeadlineNews(
        category: NewsCategory = NewsCategory.None,
        country: String = "",
        page: Int = 1,
        pageSize: Int = 6,
        apiKey: String,
    ): LiveData<Resource<List<News>>>

    fun getNews(
        query: String,
        apiKey: String
    ): Flow<PagingData<News>>

    fun getNewsByType(
        type: NewsType,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): LiveData<Resource<List<News>>>

    fun searchNews(
        query: String,
        apiKey: String
    ): LiveData<PagingData<News>>

}
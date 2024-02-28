package com.firstnews.app.domain.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.domain.repository.NewsRepository
import com.firstnews.app.domain.usecase.NewsUseCase
import kotlinx.coroutines.flow.Flow

class NewsInteractor(private val repository: NewsRepository) : NewsUseCase {

    override fun getHeadlineNews(
        category: NewsCategory,
        country: String,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): LiveData<Resource<List<News>>> {
        return repository.getHeadlineNews(category, country, page, pageSize, apiKey)
    }

    override fun getNews(query: String, apiKey: String): Flow<PagingData<News>> {
        return repository.getNews(query, apiKey)
    }

    override fun getNewsByType(
        type: NewsType,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): LiveData<Resource<List<News>>> {
        return repository.getNewsByType(type, page, pageSize, apiKey)
    }

    override fun searchNews(query: String, apiKey: String): LiveData<PagingData<News>> {
        return repository.searchNews(query, apiKey)
    }
}
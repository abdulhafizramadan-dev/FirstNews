package com.firstnews.app.domain.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.repository.SavedNewsRepository
import com.firstnews.app.domain.usecase.SavedNewsUseCase

class SavedNewsInteractor(private val repository: SavedNewsRepository) : SavedNewsUseCase {

    override fun getSavedNewsStream(): LiveData<PagingData<News>> {
        return repository.getSavedNewsStream()
    }

    override suspend fun getSavedNews(): List<News> {
        return repository.getSavedNews()
    }

    override fun isNewsSaved(url: String): LiveData<Boolean> {
        return repository.isNewsSaved(url)
    }

    override suspend fun upsertSavedNews(news: News) {
        repository.upsertSavedNews(news)
    }

    override suspend fun deleteSavedNews(news: News) {
        repository.deleteSavedNews(news)
    }
}
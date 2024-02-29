package com.firstnews.app.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.usecase.SavedNewsUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val savedNewsUseCase: SavedNewsUseCase) : ViewModel() {

    fun isNewsSaved(url: String) = savedNewsUseCase.isNewsSaved(url)

    fun insertSavedNews(news: News) {
        viewModelScope.launch {
            savedNewsUseCase.upsertSavedNews(news)
        }
    }

    fun deleteSavedNews(news: News) {
        viewModelScope.launch {
            savedNewsUseCase.deleteSavedNews(news)
        }
    }

}
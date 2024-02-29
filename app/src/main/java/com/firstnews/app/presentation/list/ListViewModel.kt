package com.firstnews.app.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.usecase.NewsUseCase

class ListViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    fun getHeadlineNews(
        country: String = "us",
        category: NewsCategory = NewsCategory.None,
        apiKey: String = "c5afeb28f0d4496bb06e0083836f80f7"
    ) = newsUseCase.getHeadlineNews(
        country = country,
        category = category,
        apiKey = apiKey
    ).cachedIn(viewModelScope)

}
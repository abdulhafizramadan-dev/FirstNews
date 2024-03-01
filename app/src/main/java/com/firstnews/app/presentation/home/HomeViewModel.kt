package com.firstnews.app.presentation.home

import androidx.lifecycle.ViewModel
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType
import com.firstnews.app.domain.usecase.NewsUseCase

class HomeViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getHeadlineNews(
        country: String = "us",
        category: NewsCategory = NewsCategory.None,
        page: Int = 1,
        pageSize: Int = 6,
        apiKey: String = "b8bd7756346948e597aca1d162f5ed76"
    ) = newsUseCase.getHeadlineNews(
        country = country,
        category = category,
        page = page,
        pageSize = pageSize,
        apiKey = apiKey
    )

}
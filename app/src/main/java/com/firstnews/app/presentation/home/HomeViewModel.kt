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
        apiKey: String = "c5afeb28f0d4496bb06e0083836f80f7"
    ) = newsUseCase.getHeadlineNews(
        country = country,
        category = category,
        page = page,
        pageSize = pageSize,
        apiKey = apiKey
    )

}
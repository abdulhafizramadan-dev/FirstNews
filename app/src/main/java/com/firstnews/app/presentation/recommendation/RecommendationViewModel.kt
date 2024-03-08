package com.firstnews.app.presentation.recommendation

import androidx.lifecycle.ViewModel
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.usecase.NewsUseCase

class RecommendationViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getHeadlineNews(
        country: String = "us",
        category: NewsCategory = NewsCategory.None,
        page: Int = 1,
        pageSize: Int = 6,
        apiKey: String = "f9f466cbc2014d73a6f09567baceb26f"
    ) = newsUseCase.getHeadlineNews(
        country = country,
        category = category,
        page = page,
        pageSize = pageSize,
        apiKey = apiKey
    )

}
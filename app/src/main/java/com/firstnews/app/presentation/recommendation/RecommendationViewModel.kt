package com.firstnews.app.presentation.recommendation

import androidx.lifecycle.ViewModel
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.usecase.NewsUseCase

class RecommendationViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getBusinessRecommendation(
        page: Int = 1,
        pageSize: Int = 10,
        apiKey: String = "b8bd7756346948e597aca1d162f5ed76"
    ) = newsUseCase.getHeadlineNews(category = NewsCategory.Business, country = "us", page = page, pageSize = pageSize, apiKey = apiKey)

    fun getTechnologyRecommendation(
        page: Int = 1,
        pageSize: Int = 4,
        apiKey: String = "b8bd7756346948e597aca1d162f5ed76"
    ) = newsUseCase.getHeadlineNews(category = NewsCategory.Technology, country = "us", page = page, pageSize = pageSize, apiKey = apiKey)

    fun getSportRecommendation(
        page: Int = 1,
        pageSize: Int = 10,
        apiKey: String = "b8bd7756346948e597aca1d162f5ed76"
    ) = newsUseCase.getHeadlineNews(category = NewsCategory.Sports, country = "us", page = page, pageSize = pageSize, apiKey = apiKey)

    fun getGeneralRecommendation(
        page: Int = 1,
        pageSize: Int = 6,
        apiKey: String = "b8bd7756346948e597aca1d162f5ed76"
    ) = newsUseCase.getHeadlineNews(category = NewsCategory.General, country = "us", page = page, pageSize = pageSize, apiKey = apiKey)

    fun getHealthRecommendation(
        page: Int = 1,
        pageSize: Int = 10,
        apiKey: String = "b8bd7756346948e597aca1d162f5ed76"
    ) = newsUseCase.getHeadlineNews(category = NewsCategory.Health, country = "us", page = page, pageSize = pageSize, apiKey = apiKey)

}
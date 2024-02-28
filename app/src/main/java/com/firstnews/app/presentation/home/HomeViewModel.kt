package com.firstnews.app.presentation.home

import androidx.lifecycle.ViewModel
import com.firstnews.app.domain.model.NewsType
import com.firstnews.app.domain.usecase.NewsUseCase

class HomeViewModel(
    private val newsUseCase: NewsUseCase
) : ViewModel() {

    fun getHeadlineNews(country: String = "us", apiKey: String = "c5afeb28f0d4496bb06e0083836f80f7") = newsUseCase.getHeadlineNews(country = country, apiKey = apiKey)

    fun getNews(
        type: NewsType,
        page: Int = 1,
        pageSize: Int = 6,
        apiKey: String = "c5afeb28f0d4496bb06e0083836f80f7"
    ) = newsUseCase.getNewsByType(type, page, pageSize, apiKey)

}
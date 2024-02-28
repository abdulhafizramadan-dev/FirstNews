package com.firstnews.app.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.firstnews.app.domain.usecase.NewsUseCase

class SearchViewModel(private val useCase: NewsUseCase) : ViewModel() {

    fun searchNews(query: String) = useCase
        .searchNews(query = query, apiKey = "c5afeb28f0d4496bb06e0083836f80f7")
        .cachedIn(viewModelScope)

}
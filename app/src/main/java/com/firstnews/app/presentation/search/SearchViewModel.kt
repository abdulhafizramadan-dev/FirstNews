package com.firstnews.app.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.firstnews.app.domain.usecase.NewsUseCase

class SearchViewModel(private val useCase: NewsUseCase) : ViewModel() {

    fun searchNews(query: String) = useCase
        .searchNews(query = query, apiKey = "f9f466cbc2014d73a6f09567baceb26f")
        .cachedIn(viewModelScope)

}
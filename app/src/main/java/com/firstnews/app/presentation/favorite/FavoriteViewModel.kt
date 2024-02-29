package com.firstnews.app.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.firstnews.app.domain.usecase.SavedNewsUseCase

class FavoriteViewModel(private val useCase: SavedNewsUseCase) : ViewModel() {

    private var _isLoad = MutableLiveData(false)
    val isLoad: LiveData<Boolean> get() = _isLoad

    fun getSavedNews() = useCase.getSavedNewsStream()
        .cachedIn(viewModelScope)

    fun setIsLoad(isLoad: Boolean) {
        _isLoad.value = isLoad
    }

}
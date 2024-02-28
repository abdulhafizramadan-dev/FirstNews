package com.firstnews.app.util

import com.kennyc.view.MultiStateView

fun MultiStateView.showContent() {
    viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showLoading() {
    viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showEmpty() {
    viewState = MultiStateView.ViewState.EMPTY
}

fun MultiStateView.showError() {
    viewState = MultiStateView.ViewState.ERROR
}

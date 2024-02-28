package com.firstnews.app.ui.listener

import com.firstnews.app.domain.model.News

interface OnMovieClickListener {
        fun onMovieClick(news: News)
        fun onSaveMovieClick(news: News)
    }
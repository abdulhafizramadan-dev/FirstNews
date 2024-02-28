package com.firstnews.app.di

import com.firstnews.app.presentation.recommendation.RecommendationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recommendationModule = module {
    viewModel { RecommendationViewModel(get()) }
}

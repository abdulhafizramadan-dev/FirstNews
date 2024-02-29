package com.firstnews.app.di

import com.firstnews.app.domain.interactor.NewsInteractor
import com.firstnews.app.domain.interactor.SavedNewsInteractor
import com.firstnews.app.domain.usecase.NewsUseCase
import com.firstnews.app.domain.usecase.SavedNewsUseCase
import org.koin.dsl.module

val domainModule = module {
    single<NewsUseCase> { NewsInteractor(get()) }
    single<SavedNewsUseCase> { SavedNewsInteractor(get()) }
}

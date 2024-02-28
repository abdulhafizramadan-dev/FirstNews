package com.firstnews.app.di

import com.firstnews.app.domain.interactor.NewsInteractor
import com.firstnews.app.domain.usecase.NewsUseCase
import org.koin.dsl.module

val domainModule = module {
    single<NewsUseCase> { NewsInteractor(get()) }
}

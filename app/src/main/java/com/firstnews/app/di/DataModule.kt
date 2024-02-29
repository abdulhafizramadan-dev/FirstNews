package com.firstnews.app.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.firstnews.app.data.NewsRepositoryImpl
import com.firstnews.app.data.SavedNewsRepositoryImpl
import com.firstnews.app.data.local.FirstNewsDatabase
import com.firstnews.app.data.remote.service.NewsApiService
import com.firstnews.app.domain.repository.NewsRepository
import com.firstnews.app.domain.repository.SavedNewsRepository
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(get())
                    .collector(ChuckerCollector(get()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(get())
            .build()
    }
    single<NewsApiService> {
        get<Retrofit>().create()
    }
    single {
        Room.databaseBuilder(
            context = get(),
            klass = FirstNewsDatabase::class.java,
            name = FirstNewsDatabase.DATABASE_NAME
        ).build()
    }
    single<NewsRepository> {
        NewsRepositoryImpl(get(), get())
    }
    single<SavedNewsRepository> { SavedNewsRepositoryImpl(get<FirstNewsDatabase>().savedNewsDao) }
}

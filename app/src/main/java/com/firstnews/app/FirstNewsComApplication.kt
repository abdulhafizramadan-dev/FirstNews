package com.firstnews.app

import android.app.Application
import com.firstnews.app.di.dataModule
import com.firstnews.app.di.domainModule
import com.firstnews.app.di.homeModule
import com.firstnews.app.di.listModule
import com.firstnews.app.di.recommendationModule
import com.firstnews.app.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FirstNewsComApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                domainModule,
                dataModule,
                homeModule,
                recommendationModule,
                searchModule,
                listModule
            )
        }
    }
}
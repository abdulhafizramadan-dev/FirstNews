package com.firstnews.app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.map
import com.firstnews.app.data.local.dao.SavedNewsDao
import com.firstnews.app.data.mapper.toDomain
import com.firstnews.app.data.mapper.toDomains
import com.firstnews.app.data.mapper.toNewsDomain
import com.firstnews.app.data.mapper.toNewsDomains
import com.firstnews.app.data.mapper.toSavedNewsEntity
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.repository.SavedNewsRepository

class SavedNewsRepositoryImpl(private val savedNewsDao: SavedNewsDao) : SavedNewsRepository {

    override fun getSavedNewsStream(): LiveData<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { savedNewsDao.getSavedNewsStream() }
        ).liveData.map { pagingData ->
            pagingData.map { it.toNewsDomain() }
        }
    }

    override suspend fun getSavedNews(): List<News> {
        return savedNewsDao.getSavedNews().map { it.toNewsDomain() }
    }

    override fun isNewsSaved(url: String): LiveData<Boolean> {
        return savedNewsDao.isNewsSaved(url)
    }

    override suspend fun upsertSavedNews(news: News) {
        return savedNewsDao.upsertSavedNews(news.toSavedNewsEntity())
    }

    override suspend fun deleteSavedNews(news: News) {
        return savedNewsDao.deleteSavedNews(news.url)
    }
}
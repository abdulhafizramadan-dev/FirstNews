package com.firstnews.app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.map
import androidx.room.withTransaction
import com.firstnews.app.data.paging.NewsRemoteMediator
import com.firstnews.app.data.local.FirstNewsDatabase
import com.firstnews.app.data.mapper.toDomain
import com.firstnews.app.data.mapper.toDomains
import com.firstnews.app.data.mapper.toNewsEntities
import com.firstnews.app.data.paging.HeadlineNewsPagingSource
import com.firstnews.app.data.paging.NewsPagingSource
import com.firstnews.app.data.remote.service.NewsApiService
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType
import com.firstnews.app.domain.model.Resource
import com.firstnews.app.domain.repository.NewsRepository
import com.firstnews.app.util.wrapEspressoIdlingResource
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val apiService: NewsApiService,
    private val database: FirstNewsDatabase
) : NewsRepository {

    override fun getHeadlineNews(
        category: NewsCategory,
        country: String,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): LiveData<Resource<List<News>>> = liveData {
        wrapEspressoIdlingResource {
            emit(Resource.Loading)
            try {
                val newsResponse = apiService.getTopHeadlines(
                    category = category.route,
                    country = country,
                    page = page,
                    pageSize = pageSize,
                    apiKey = apiKey
                )
                when(newsResponse) {
                    is NetworkResponse.Success -> {
                        val newsDao = database.newsDao
                        database.withTransaction {
                            val newsEntities = newsResponse.body.data?.toNewsEntities(type = NewsType.HeadlineNews, category = category) ?: emptyList()
                            if (category == NewsCategory.None) {
                                newsDao.clearNewsByType(NewsType.HeadlineNews)
                            } else {
                                newsDao.clearNewsByCategory(category)
                            }
                            newsDao.upsertNews(newsEntities)
                        }
                        val newsEntities = if (category == NewsCategory.None) newsDao.getNewsByType(NewsType.HeadlineNews) else
                            newsDao.getNewsByCategory(category)
                        val resource: LiveData<Resource<List<News>>> = newsEntities.map { news ->
                            Resource.Success(news.toDomains())
                        }
                        emitSource(resource)
                    }
                    is NetworkResponse.Error -> {
                        val newsDao = database.newsDao
                        val message = newsResponse.body?.message ?: newsResponse.error?.message
                        val newsEntities = if (category == NewsCategory.None) newsDao.getNewsByType(NewsType.HeadlineNews) else
                            newsDao.getNewsByCategory(category)
                        val resource: LiveData<Resource<List<News>>> = newsEntities.map {
                            Resource.Error(error = Throwable(message), data = it.toDomains())
                        }
                        emitSource(resource)
                    }
                }
            } catch (error: Exception) {
                val headlineNewsDomain = database.newsDao.getNews(NewsType.HeadlineNews).toDomains()
                emit(Resource.Error(error = error, data = headlineNewsDomain))
            }
        }
    }

    override fun getHeadlineNews(
        category: NewsCategory,
        country: String,
        apiKey: String
    ): LiveData<PagingData<News>> = wrapEspressoIdlingResource {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                HeadlineNewsPagingSource(apiService, category, country, apiKey)
            }
        ).liveData
    }

    override fun getNewsByType(
        type: NewsType,
        page: Int,
        pageSize: Int,
        apiKey: String
    ): LiveData<Resource<List<News>>> = liveData {
        wrapEspressoIdlingResource {
            emit(Resource.Loading)
            try {
                val newsResponse = apiService.getEverything(
                    query = type.name,
                    page = page,
                    pageSize = pageSize,
                    apiKey = apiKey
                )
                when(newsResponse) {
                    is NetworkResponse.Success -> {
                        val newsDao = database.newsDao
                        database.withTransaction {
                            val newsEntities = newsResponse.body.data?.toNewsEntities(type) ?: emptyList()
                            newsDao.clearNewsByType(type)
                            newsDao.upsertNews(newsEntities)
                        }
                        val news = newsDao.getNews(type).toDomains()
                        emit(Resource.Success(news))
                    }
                    is NetworkResponse.Error -> {
                        val message = newsResponse.body?.message ?: newsResponse.error?.message
                        val headlineNewsDomain = database.newsDao.getNews(type).toDomains()
                        emit(Resource.Error(error = Throwable(message), data = headlineNewsDomain))
                    }
                }
            } catch (error: Exception) {
                val headlineNewsDomain = database.newsDao.getNews(type).toDomains()
                emit(Resource.Error(error = error, data = headlineNewsDomain))
            }
        }
    }

    @ExperimentalPagingApi
    override fun getNews(query: String, apiKey: String): Flow<PagingData<News>> = wrapEspressoIdlingResource {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                database.newsDao.getNewsPagingSource()
            },
            remoteMediator = NewsRemoteMediator(apiService, database, apiKey, query)
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchNews(query: String, apiKey: String): LiveData<PagingData<News>> = wrapEspressoIdlingResource {
        Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                NewsPagingSource(apiService, query, apiKey)
            }
        ).liveData
    }

}
package com.firstnews.app.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.firstnews.app.data.local.FirstNewsDatabase
import com.firstnews.app.data.local.entity.NewsEntity
import com.firstnews.app.data.local.entity.RemoteKey
import com.firstnews.app.data.mapper.toNewsEntities
import com.firstnews.app.data.remote.service.NewsApiService
import com.firstnews.app.domain.model.NewsType
import com.haroldadmin.cnradapter.NetworkResponse

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val apiService: NewsApiService,
    private val database: FirstNewsDatabase,
    private val apiKey: String,
    private val query: String,
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val newsResponse = apiService.getEverything(
                query = query,
                page = page,
                apiKey = apiKey,
                pageSize = state.config.pageSize,
            )

            when (newsResponse) {
                is NetworkResponse.Success -> {
                    val newsEntities = newsResponse.body.data?.toNewsEntities(NewsType.Recommendation) ?: emptyList()
                    val endOfPaginationReached = newsEntities.isEmpty()

                    database.withTransaction {
                        val newsDao = database.newsDao
                        val remoteKeyDao = database.remoteKeyDao

                        if (loadType == LoadType.REFRESH) {
                            newsDao.clearNewsByType(NewsType.Recommendation)
                            remoteKeyDao.clearRemoteKeys()
                        }

                        val prevPage = if (page > 1) page - 1 else null
                        val nextPage = if (!endOfPaginationReached) page + 1 else null

                        newsDao.upsertNews(newsEntities = newsEntities)

                        val remoteKeyEntities = newsEntities.map { news ->
                            RemoteKey(
                                newsUrl = news.url,
                                prevKey = prevPage,
                                currentKey = page,
                                nextKey = nextPage
                            )
                        }
                        remoteKeyDao.upsertRemoteKeys(remoteKeys = remoteKeyEntities)
                    }
                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                is NetworkResponse.Error -> {
                    val message = newsResponse.body?.message ?: newsResponse.error?.message
                    return MediatorResult.Error(Throwable(message))
                }
            }
        } catch (error: Throwable) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, NewsEntity>
    ): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { news ->
            database.remoteKeyDao.remoteKeyByNewsUrl(news.url)
        }
    }
}
package com.firstnews.app.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.firstnews.app.data.mapper.toDomains
import com.firstnews.app.data.remote.service.NewsApiService
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.util.wrapEspressoIdlingResource
import com.haroldadmin.cnradapter.NetworkResponse

class HeadlineNewsPagingSource(
    private val apiService: NewsApiService,
    private val category: NewsCategory,
    private val county: String,
    private val apiKey: String
) : PagingSource<Int, News>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return wrapEspressoIdlingResource {
            try {
                val nextPageNumber = params.key ?: 1
                val newsResponse = apiService.getTopHeadlines(
                    country = county,
                    category = category.route,
                    page = nextPageNumber,
                    apiKey = apiKey,
                    pageSize = params.loadSize
                )
                when (newsResponse) {
                    is NetworkResponse.Success -> {
                        val newsDomain = newsResponse.body.data?.toDomains() ?: emptyList()
                        LoadResult.Page(
                            data = newsDomain,
                            prevKey = null,
                            nextKey = nextPageNumber + 1
                        )
                    }
                    is NetworkResponse.Error -> {
                        val message = newsResponse.body?.message ?: newsResponse.error?.message
                        LoadResult.Error(Throwable(message))
                    }
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
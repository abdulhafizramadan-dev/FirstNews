package com.firstnews.app.data.remote.service

import com.firstnews.app.data.remote.response.CommonErrorResponse
import com.firstnews.app.data.remote.response.CommonResponse
import com.firstnews.app.data.remote.response.NewsResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String,
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String
    ): NetworkResponse<CommonResponse<List<NewsResponse>>, CommonErrorResponse>

    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String,
    ): NetworkResponse<CommonResponse<List<NewsResponse>>, CommonErrorResponse>

    @GET("everything")
    suspend fun getEverythingByCategory(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String,
    ): NetworkResponse<CommonResponse<List<NewsResponse>>, CommonErrorResponse>

}
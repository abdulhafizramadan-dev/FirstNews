package com.firstnews.app.data.mapper

import com.firstnews.app.data.local.entity.NewsEntity
import com.firstnews.app.data.local.entity.SavedNewsEntity
import com.firstnews.app.data.remote.response.NewsResponse
import com.firstnews.app.domain.model.News
import com.firstnews.app.domain.model.NewsCategory
import com.firstnews.app.domain.model.NewsType

/**
 * Mapper from NewsResponse to NewsEntity
 */

fun List<NewsResponse>.toNewsEntities(type: NewsType, category: NewsCategory = NewsCategory.None): List<NewsEntity> = map {
    it.toNewsEntity(type, category)
}

fun NewsResponse.toNewsEntity(type: NewsType, category: NewsCategory): NewsEntity =
    NewsEntity(
        urlToImage = urlToImage,
        title = title ?: "",
        url = url ?: "",
        publishedAt = publishedAt ?: "",
        author = author ?: "",
        source = source?.name ?: "",
        type = type,
        category = category
    )


/**
 * Mapper from NewsEntity to News Domain
 */
@JvmName("newsEntityToNewsDomain")
fun List<NewsEntity>.toDomains(): List<News> = map {
    it.toDomain()
}

fun NewsEntity.toDomain(): News =
    News(
        urlToImage = urlToImage,
        title = title,
        url = url,
        publishedAt = publishedAt,
        author = author,
        source = source
    )


/**
 * Mapper from NewsResponse to News Domain
 */

@JvmName("newsResponseToNewsDomain")
fun List<NewsResponse>.toDomains(): List<News> = map {
    it.toDomain()
}

fun NewsResponse.toDomain(): News =
    News(
        urlToImage = urlToImage,
        title = title ?: "",
        url = url ?: "",
        publishedAt = publishedAt ?: "",
        author = author ?: "",
        source = source?.name ?: ""
    )


/**
 * Mapper from NewsEntity to SavedNewsEntity
 */

fun News.toSavedNewsEntity(): SavedNewsEntity =
    SavedNewsEntity(
        urlToImage = urlToImage,
        title = title,
        url = url,
        publishedAt = publishedAt,
        author = author,
        source = source
    )

fun List<SavedNewsEntity>.toNewsDomains(): List<News> = map {
    it.toNewsDomain()
}

fun SavedNewsEntity.toNewsDomain(): News =
    News(
        urlToImage = urlToImage,
        title = title,
        url = url,
        publishedAt = publishedAt,
        author = author,
        source = source
    )
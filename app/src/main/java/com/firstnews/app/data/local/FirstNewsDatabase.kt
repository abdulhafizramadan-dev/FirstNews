package com.firstnews.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.firstnews.app.data.local.dao.NewsDao
import com.firstnews.app.data.local.dao.RemoteKeyDao
import com.firstnews.app.data.local.dao.SavedNewsDao
import com.firstnews.app.data.local.entity.NewsEntity
import com.firstnews.app.data.local.entity.RemoteKey
import com.firstnews.app.data.local.entity.SavedNewsEntity

@Database(
    entities = [
        SavedNewsEntity::class,
        NewsEntity::class,
        RemoteKey::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class FirstNewsDatabase : RoomDatabase() {


    abstract val newsDao: NewsDao

    abstract val savedNewsDao: SavedNewsDao

    abstract val remoteKeyDao: RemoteKeyDao

    companion object {
        const val DATABASE_NAME = "first-news.db"
    }

}

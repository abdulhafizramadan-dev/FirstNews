package com.firstnews.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.firstnews.app.data.local.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun upsertRemoteKeys(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE newsUrl = :newsUrl")
    suspend fun remoteKeyByNewsUrl(newsUrl: String): RemoteKey

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
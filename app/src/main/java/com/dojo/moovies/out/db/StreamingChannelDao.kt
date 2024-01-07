package com.dojo.moovies.out.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.out.db.entity.StreamingChannelEntity
import com.dojo.moovies.out.db.entity.TABLE_NAME_MY_LIST
import com.dojo.moovies.out.db.entity.TABLE_NAME_STREAMING_INFO
import kotlinx.coroutines.flow.Flow

@Dao
interface StreamingChannelDao {

    @Query("SELECT * FROM $TABLE_NAME_STREAMING_INFO")
    fun findAll(): Flow<List<StreamingChannelEntity>>

    @Query("SELECT * FROM $TABLE_NAME_STREAMING_INFO WHERE id = :id")
    fun findById(id: String): Flow<StreamingChannelEntity?>

    @Query("DELETE FROM $TABLE_NAME_STREAMING_INFO WHERE id = :id")
    suspend fun delete(id: String)

    @Upsert
    suspend fun update(streamingChannelEntity: StreamingChannelEntity)

    @Query("DELETE FROM $TABLE_NAME_STREAMING_INFO")
    suspend fun deleteAll()

}

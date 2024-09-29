package com.dojo.moovies.out.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.out.db.entity.TABLE_NAME_MY_LIST
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {

    @Query("SELECT * FROM $TABLE_NAME_MY_LIST")
    fun findAll(): Flow<List<MyListEntity>>

    @Query("SELECT * FROM $TABLE_NAME_MY_LIST LIMIT 20")
    fun findAllLimit20(): Flow<List<MyListEntity>>

    @Query("SELECT * FROM $TABLE_NAME_MY_LIST WHERE mooviesId = :id")
    suspend fun findByMooviesId(id: String): MyListEntity?

    @Query("SELECT * FROM $TABLE_NAME_MY_LIST WHERE id = :id AND mediaType = :mediaType")
    suspend fun findByIdAndMediaType(id: Long, mediaType: String): MyListEntity?

    @Query("DELETE FROM $TABLE_NAME_MY_LIST WHERE id = :id AND mediaType = :mediaType")
    suspend fun deleteByIdAndMediaType(id: Long, mediaType: String)

    @Upsert
    suspend fun update(myListEntity: MyListEntity)

    @Query("DELETE FROM $TABLE_NAME_MY_LIST")
    suspend fun deleteAll()

    @Query("UPDATE $TABLE_NAME_MY_LIST SET watched = :watched WHERE id = :id AND mediaType = :mediaType")
    suspend fun updateWatched(id: Long, watched: Boolean, mediaType: String)
}

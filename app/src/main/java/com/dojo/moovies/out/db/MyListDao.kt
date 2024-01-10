package com.dojo.moovies.out.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.out.db.entity.TABLE_NAME_MY_LIST
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {

    @Query("SELECT * FROM $TABLE_NAME_MY_LIST")
    fun findAll(): Flow<List<MyListEntity>>


    @Query("SELECT * FROM $TABLE_NAME_MY_LIST WHERE mooviesId = :id")
    fun findByMooviesId(id: String): Flow<MyListEntity?>

    @Query("DELETE FROM $TABLE_NAME_MY_LIST WHERE mooviesId = :id")
    suspend fun delete(id: String)

    @Upsert
    suspend fun update(myListEntity: MyListEntity)

    @Query("DELETE FROM $TABLE_NAME_MY_LIST")
    suspend fun deleteAll()
}

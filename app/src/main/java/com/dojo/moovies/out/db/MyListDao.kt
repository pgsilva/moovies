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

    @Query("SELECT * FROM $TABLE_NAME_MY_LIST WHERE id = :taskId")
    fun findById(taskId: String): Flow<MyListEntity?>

    @Query("DELETE FROM $TABLE_NAME_MY_LIST WHERE id = :taskId")
    suspend fun delete(taskId: String)

    @Upsert
    suspend fun update(taskEntity: MyListEntity)

    @Query("DELETE FROM $TABLE_NAME_MY_LIST")
    suspend fun deleteAll()
}

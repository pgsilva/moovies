package com.dojo.moovies.repository

import com.dojo.moovies.core.domain.MooviesData
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.repository.mapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyListRepository(
    private val dao: MyListDao
) {
    suspend fun findAll(): Flow<List<MooviesData>> {
        return dao.findAll().map { list->
            list.map { it.toDomain() }
        }
    }
}
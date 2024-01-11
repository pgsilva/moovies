package com.dojo.moovies.repository

import com.dojo.moovies.core.domain.MooviesDataSimplified
import com.dojo.moovies.out.db.MyListDao
import com.dojo.moovies.out.db.entity.MyListEntity
import com.dojo.moovies.repository.mapper.toDomain
import com.dojo.moovies.repository.mapper.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MyListRepository(
    private val dao: MyListDao
) {
    fun findAll(): Flow<List<MooviesDataSimplified>> {
        return dao.findAll().map { list ->
            list.map { it.toDomain() }
        }
    }

    fun findAllLimit20(): Flow<List<MooviesDataSimplified>> {
        return dao.findAllLimit20().map { list ->
            list.map { it.toDomain() }
        }
    }

    suspend fun findByIdAndMediaType(detailMap: Pair<Int, String>): MooviesDataSimplified? {
        var response: MyListEntity?
        withContext(Dispatchers.IO) {
            response = dao.findByIdAndMediaType(detailMap.first.toLong(), detailMap.second)
        }

        return response?.toDomain()
    }

    suspend fun upsert(domain: MooviesDataSimplified) {
        val entity = domain.toEntity()
        dao.update(entity)
    }

    suspend fun remove(domain: MooviesDataSimplified) {
        val entity = domain.toEntity()
        dao.deleteByIdAndMediaType(entity.id, entity.mediaType)
    }

}

package com.noblecilla.listmaker.model

import com.noblecilla.listmaker.vo.Result

interface ListDataSource {
    suspend fun all(): Result<List<ListEntity>>
    suspend fun insert(listEntity: ListEntity): Result<Boolean>
    suspend fun delete(listEntity: ListEntity): Result<Boolean>
    suspend fun items(listEntity: ListEntity): Result<ListEntity>
    suspend fun insertItem(listEntity: ListEntity, item: String): Result<Boolean>
    suspend fun deleteItem(listEntity: ListEntity, item: String): Result<Boolean>
}

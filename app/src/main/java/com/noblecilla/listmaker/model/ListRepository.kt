package com.noblecilla.listmaker.model

import android.content.Context
import com.noblecilla.listmaker.data.DataManager
import com.noblecilla.listmaker.vo.Result

class ListRepository(private val context: Context) : ListDataSource {
    override suspend fun all(): Result<List<ListEntity>> {
        return try {
            Result.Success(DataManager.all(context))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insert(listEntity: ListEntity): Result<Boolean> {
        return try {
            DataManager.insert(context, listEntity)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun delete(listEntity: ListEntity): Result<Boolean> {
        return try {
            DataManager.delete(context, listEntity)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun items(listEntity: ListEntity): Result<ListEntity> {
        return try {
            Result.Success(DataManager.items(context, listEntity))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insertItem(listEntity: ListEntity, item: String): Result<Boolean> {
        return try {
            listEntity.items.add(item)
            DataManager.insert(context, listEntity)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteItem(listEntity: ListEntity, item: String): Result<Boolean> {
        return try {
            listEntity.items.remove(item)
            DataManager.insert(context, listEntity)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

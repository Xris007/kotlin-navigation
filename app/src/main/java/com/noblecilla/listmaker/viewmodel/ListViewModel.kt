package com.noblecilla.listmaker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noblecilla.listmaker.model.ListDataSource
import com.noblecilla.listmaker.model.ListEntity
import com.noblecilla.listmaker.vo.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(private val dataSource: ListDataSource) : ViewModel() {

    private val _list = MutableLiveData<List<ListEntity>>()
    val list: LiveData<List<ListEntity>> = _list

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _items = MutableLiveData<ListEntity>()
    val items: LiveData<ListEntity> = _items

    private val _listUpdated = MutableLiveData<Boolean>()
    val listUpdated: LiveData<Boolean> = _listUpdated

    fun all() = viewModelScope.launch {
        val result: Result<List<ListEntity>> = withContext(Dispatchers.IO) {
            dataSource.all()
        }

        when (result) {
            is Result.Success -> _list.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun insert(listEntity: ListEntity) = viewModelScope.launch {
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.insert(listEntity)
        }

        when (result) {
            is Result.Success -> _isSuccessful.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun delete(listEntity: ListEntity) = viewModelScope.launch {
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.delete(listEntity)
        }

        when (result) {
            is Result.Success -> _isSuccessful.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun items(listEntity: ListEntity) = viewModelScope.launch {
        val result: Result<ListEntity> = withContext(Dispatchers.IO) {
            dataSource.items(listEntity)
        }

        when (result) {
            is Result.Success -> _items.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun insertItem(listEntity: ListEntity, item: String) = viewModelScope.launch {
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.insertItem(listEntity, item)
        }

        when (result) {
            is Result.Success -> _listUpdated.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }

    fun deleteItem(listEntity: ListEntity, item: String) = viewModelScope.launch {
        val result: Result<Boolean> = withContext(Dispatchers.IO) {
            dataSource.deleteItem(listEntity, item)
        }

        when (result) {
            is Result.Success -> _listUpdated.value = result.data
            is Result.Error -> _onMessageError.value = result.exception
        }
    }
}

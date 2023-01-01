package com.example.todoapplication.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.data.ToDoDatabase
import com.example.todoapplication.data.models.ToDoData
import com.example.todoapplication.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions:
 */
class ToDoViewModel(application: Application): AndroidViewModel(application) {

    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository : ToDoRepository

    val getAllData: LiveData<List<ToDoData>>
    val sortyByHighPriority: LiveData<List<ToDoData>>
    val sortyByLowPriority: LiveData<List<ToDoData>>

    init {
        repository = ToDoRepository(toDoDao)
        getAllData = repository.getAllData
        sortyByHighPriority = repository.sortByHighPriority
        sortyByLowPriority = repository.sortByLowPriority
    }

    // 插入一条数据 注意当前采用的模式如果数据已经存在会报错
    fun instertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.instertData(toDoData)
        }
    }

    // 更新一条数据
    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    // 删除一条数据
    fun deleteItem(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(toDoData)
        }
    }

    // 删除所有数据
    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    // 搜索笔记
    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>{
        return repository.searchDatabase(searchQuery)
    }
}
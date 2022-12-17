package com.example.todoapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.todoapplication.data.ToDoDao
import com.example.todoapplication.data.models.ToDoData

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions:
 */
class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    /**
     * 插入数据 IGNORE：有旧数据存在，想要插入的数据将会插入失败（默认采用的策略）
     */
    suspend fun instertData(toDoData: ToDoData) {
        toDoDao.instertData(toDoData)
    }

    /**
     * 更新数据
     */
    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updataData(toDoData)
    }

    /**
     * 删除一条数据
     */
    suspend fun deleteItem(toDoData: ToDoData) {
        toDoDao.deleteItem(toDoData)
    }

    /**
     * 删除所有便签
     */
    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }
}
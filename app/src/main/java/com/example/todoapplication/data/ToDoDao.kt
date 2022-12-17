package com.example.todoapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoapplication.data.models.ToDoData

/**
 * Created by 公众号：IT波 on 2022/12/10 Copyright © Leon. All rights reserved.
 * Functions: 插入数据接口
 *
 * OnConflictStrategy.IGNORE：有旧数据存在，想要插入的数据将会插入失败（默认采用的策略）
 * OnConflictStrategy.REPLACE：有旧数据存在，则替换掉旧数据
 * OnConflictStrategy.ABORT：有旧数据存在，则终止事务
 * OnConflictStrategy.ROLLBACK：有旧数据存在，则回滚事务（已废弃，使用 ABORT 代替）
 * OnConflictStrategy.FAIL：有旧数据存在，则提示插入数据失败（已废弃，使用 ABORT 代替）
 */

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoData>>

    /** 插入数据 */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun instertData(toDoData: ToDoData)

    /** 更新数据 */
    @Update
    suspend fun updataData(toDoData: ToDoData)

    /** 删除一条数据 */
    @Delete
    suspend fun deleteItem(toDoData: ToDoData)

    /** 删除所有数据 */
    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}
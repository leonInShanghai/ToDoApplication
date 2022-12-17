package com.example.todoapplication.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapplication.data.models.Priority
import kotlinx.android.parcel.Parcelize

/**
 * Created by 公众号：IT波 on 2022/12/10 Copyright © Leon. All rights reserved.
 * Functions: 便签模型类
 */
@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable
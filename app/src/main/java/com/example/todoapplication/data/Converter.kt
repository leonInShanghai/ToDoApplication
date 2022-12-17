package com.example.todoapplication.data

import androidx.room.TypeConverter
import com.example.todoapplication.data.models.Priority

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions: 将枚举类的Priority转换为String类型
 */
class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}
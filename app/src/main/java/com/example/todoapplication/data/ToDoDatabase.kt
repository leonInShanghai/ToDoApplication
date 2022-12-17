package com.example.todoapplication.data

import android.content.Context
import androidx.room.*
import com.example.todoapplication.data.models.ToDoData

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions:
 */

@Database(entities = [ToDoData::class],version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDatabase: RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            val temppInstance = INSTANCE
            if (temppInstance != null) {
                return temppInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
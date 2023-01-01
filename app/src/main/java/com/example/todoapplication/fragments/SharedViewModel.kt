package com.example.todoapplication.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todoapplication.R
import com.example.todoapplication.data.models.Priority
import com.example.todoapplication.data.models.ToDoData

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions:
 */
class SharedViewModel(application: Application): AndroidViewModel(application) {

    // region ListFragment
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(toDoDatas: List<ToDoData>) {
        emptyDatabase.value = toDoDatas.isEmpty()
    }
    // endregion ListFtament

    // AddFragment 中 spinner 被选择了回调
    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        // 此方法这里不实现
        override fun onNothingSelected(p0: AdapterView<*>?) {}

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long) {
            when (position) {
                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat
                    .getColor(application, R.color.red))}
                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat
                    .getColor(application, R.color.yellow))}
                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat
                    .getColor(application, R.color.green))}
            }
        }
    }


    // 验证用户输入的信息是否为空
    fun verifyDataFromUser(title: String, description: String): Boolean {
//        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description))  {
//            false
//        } else {
//            !(title.isEmpty() || description.isEmpty())
//        }

        return !(title.isEmpty() || description.isEmpty())
    }

    // 将用户选择的spinner->String转换为 枚举
    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "Hight Priority" -> {
                Priority.HIGH}
            "高优先级" -> {
                Priority.HIGH}
            "높은 우선 순위" -> {
                Priority.HIGH}

            "Medium Priority" -> {
                Priority.MEDIUM}
            "中优先级" -> {
                Priority.MEDIUM}
            "보통 우선 순위" -> {
                Priority.MEDIUM}

            "Low Priority" -> {
                Priority.LOW}
            "低优先级" -> {
                Priority.LOW}
            "낮은 우선 순위" -> {
                Priority.LOW}

            else -> Priority.LOW
        }
    }
}
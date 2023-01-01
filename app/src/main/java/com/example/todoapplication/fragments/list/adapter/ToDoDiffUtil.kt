package com.example.todoapplication.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapplication.data.models.ToDoData

/**
 * Created by 公众号：IT波 on 2022/12/18 Copyright © Leon. All rights reserved.
 * Functions: 可以计算两个 List 之间的差异，得到两个 List 之间的差异集，如果 List 集合很大，计算两个 List 之间的差异耗时，
 * 应该放到子线程中执行，计算得到 DiffUtil.DiffResult 后，将该结果集应用到主线程的 RecyclerView 上。
 */
class ToDoDiffUtil(
    private val oldList: List<ToDoData>,
    private val newList: List<ToDoData>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority
                && oldList[oldItemPosition].time == newList[newItemPosition].time
    }

}
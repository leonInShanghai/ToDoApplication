package com.example.todoapplication.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.data.models.ToDoData
import com.example.todoapplication.databinding.RowLayoutBinding

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions:
 */
class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()
        private set

    class MyViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        // return MyViewHolder(view)
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    // 设置数据源的方法
    fun setData(toDoData: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)

        this.dataList = toDoData
        // 一定要记得刷新界面
        toDoDiffResult.dispatchUpdatesTo(this) // 代替notifyDataSetChanged
        // notifyDataSetChanged()
    }
}
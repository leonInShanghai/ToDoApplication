package com.example.todoapplication.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.data.models.Priority
import com.example.todoapplication.data.models.ToDoData
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.row_layout.view.*

/**
 * Created by 公众号：IT波 on 2022/12/11 Copyright © Leon. All rights reserved.
 * Functions:
 */
class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        // return MyViewHolder(view)
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // 设置标题
        holder.itemView.title_txt.text = dataList[position].title
        // 设置内容
        holder.itemView.description_txt.text = dataList[position].description

        // 设置item的点击事件
        holder.itemView.row_background.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            // 跳转到更新fragment R.id.action_listFragment_to_updateFragment
            holder.itemView.findNavController().navigate(action)
        }

        // 设置代表排序的点 颜色
        val priority = dataList[position].priority
        when (priority) {
            Priority.HIGH -> {
                holder.itemView.priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red))
            }
            Priority.MEDIUM -> {
                holder.itemView.priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.yellow))
            }
            Priority.LOW -> {
                holder.itemView.priority_indicator.setCardBackgroundColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.green))
            }
        }
    }

    // 设置数据源的方法
    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        // 一定要记得刷新界面
        notifyDataSetChanged()
    }
}
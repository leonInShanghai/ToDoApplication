package com.example.todoapplication.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.todoapplication.R
import com.example.todoapplication.data.models.Priority
import com.example.todoapplication.data.models.ToDoData
import com.example.todoapplication.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*

/**
 * Created by 公众号：IT波 on 2022/12/17 Copyright © Leon. All rights reserved.
 * Functions: Databinding 适配器
 */
class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    // 点击跳转到添加笔记fragment
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(cardView.context, R.color.red))
                }
                Priority.MEDIUM -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(cardView.context, R.color.yellow))
                }
                Priority.LOW -> {
                    cardView.setCardBackgroundColor(
                        ContextCompat.getColor(cardView.context, R.color.green))
                }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                // 跳转到更新fragment R.id.action_listFragment_to_updateFragment
                view.findNavController().navigate(action)
            }
        }
    }

}

// priority转int
//fun parsePriorityToInt(priority: Priority): Int {
//    return when (priority) {
//        Priority.HIGH -> 0
//        Priority.MEDIUM -> 1
//        Priority.LOW -> 2
//    }
//}


// 设置item的点击事件
//holder.itemView.row_background.setOnClickListener {
//    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
//    // 跳转到更新fragment R.id.action_listFragment_to_updateFragment
//    holder.itemView.findNavController().navigate(action)
//}


// 设置代表排序的点 颜色
//val priority = dataList[position].priority
//when (priority) {
//    Priority.HIGH -> {
//        holder.itemView.priority_indicator.setCardBackgroundColor(
//            ContextCompat.getColor(holder.itemView.context, R.color.red))
//    }
//    Priority.MEDIUM -> {
//        holder.itemView.priority_indicator.setCardBackgroundColor(
//            ContextCompat.getColor(holder.itemView.context, R.color.yellow))
//    }
//    Priority.LOW -> {
//        holder.itemView.priority_indicator.setCardBackgroundColor(
//            ContextCompat.getColor(holder.itemView.context, R.color.green))
//    }
//}
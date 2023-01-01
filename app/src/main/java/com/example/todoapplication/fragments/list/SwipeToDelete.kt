package com.example.todoapplication.fragments.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 公众号：IT波 on 2022/12/18 Copyright © Leon. All rights reserved.
 * Functions: item左滑删除
 */
abstract class SwipeToDelete: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }
}
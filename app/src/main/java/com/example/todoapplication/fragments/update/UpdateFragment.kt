package com.example.todoapplication.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapplication.R
import com.example.todoapplication.data.models.Priority
import com.example.todoapplication.data.models.ToDoData
import com.example.todoapplication.data.viewmodel.ToDoViewModel
import com.example.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

/**
 * Created by 公众号：IT波 on 2022/12/4 Copyright © Leon. All rights reserved.
 * Functions: 更新一个便签
 */
class UpdateFragment : Fragment() {

    // 接受传递过来的参数（列表页用户点击item）
    private val args by navArgs<UpdateFragmentArgs>()

    // 引用viewModel
    private val mSharedViewModel: SharedViewModel by viewModels()

    // 引用viewMode
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        view.current_title_et.setText(args.currentItem.title)
        view.current_description_et.setText(args.currentItem.description)
        view.current_priorities_spinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.current_priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        // 设置ActionBar右上角的按钮-1
        setHasOptionsMenu(true)

        return view
    }

    // 设置ActionBar右上角的按钮-2
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // 通过打气筒加载xml布局
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    // 设置ActionBar右上角的按钮-3 点击事件的监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 用户点击了保存按钮
        when (item.itemId) {
            R.id.menu_save -> updateItem() // 更新便签
            R.id.menu_delete -> confirmItemRemoval() // 用户点击了删除按钮
            R.id.munu_share -> {  } // 用户点击了分享
        }
        return super.onOptionsItemSelected(item)
    }

    // 确认删除？Show alert dialog to confirm removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) {_,_->
            // 用户点击了确认，执行删除
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                getString(R.string.successfully_removed, args.currentItem.title),
                Toast.LENGTH_SHORT
            ).show()
            // 返回上一页
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) {_,_-> }
        builder.setTitle(getString(R.string.delete) + "${args.currentItem.title}?")
        builder.setMessage(getString(R.string.are_you_sure) + "${args.currentItem.title}?")
        builder.create().show()
    }

    // 更新便签
    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString()
        val getPriority = current_priorities_spinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            // Update current item.
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            // Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            // 提示用户:请输入标题和内容
            Toast.makeText(requireContext(), getString(R.string.fill_out_all_fields), Toast.LENGTH_SHORT).show()
        }
    }
}
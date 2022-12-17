package com.example.todoapplication.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplication.R
import com.example.todoapplication.data.viewmodel.ToDoViewModel
import com.example.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

/**
 * Created by 公众号：IT波 on 2022/12/4 Copyright © Leon. All rights reserved.
 * Functions: 首页
 */
class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // 实例化recycerView
        val recycerView = view.recyclerView
        recycerView.adapter = adapter
        recycerView.layoutManager = LinearLayoutManager(requireActivity())
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        // 点击右下角的“+”按钮跳转到新建页面
        view.floatingActionButton.setOnClickListener {
            // 点击跳转到添加笔记fragment
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        // 设置ActionBar右上角的按钮-1
        setHasOptionsMenu(true)

        return view
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_data_textView?.visibility = View.VISIBLE
        } else {
            view?.no_data_imageView?.visibility = View.INVISIBLE
            view?.no_data_textView?.visibility = View.INVISIBLE
        }
    }

    // 设置ActionBar右上角的按钮-2
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    // 设置ActionBar右上角的按钮-3 点击事件的监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            // 再次确认，删除全部数据
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // 再次弹窗确认是否要删除全部便签 Show alert dialog to confirm removal of all from database table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) {_,_->
            // 用户点击了确认，执行删除
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                getString(R.string.successfully_removed_all),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton(getString(R.string.no)) {_,_-> }
        builder.setTitle(getString(R.string.delete_all))
        builder.setMessage(getString(R.string.are_you_sure_all))
        builder.create().show()
    }
}
package com.example.todoapplication.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.data.models.Priority
import com.example.todoapplication.data.models.ToDoData
import com.example.todoapplication.data.viewmodel.ToDoViewModel
import com.example.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

/**
 * Created by 公众号：IT波 on 2022/12/4 Copyright © Leon. All rights reserved.
 * Functions: 增加新便签
 */
class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_add, container, false)

        // 设置ActionBar右上角的按钮-1
        setHasOptionsMenu(true)

        // 设置用户选择优先级spinner显示不同的颜色
        view.priorities_spinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }

    // 设置ActionBar右上角的按钮-2
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    // 设置ActionBar右上角的按钮-3 点击事件的监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            instertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 将用户新建的便签写入到本地数据库
     */
    private fun instertDataToDb() {
        val mTitle = title_et.text.toString()
        val mPriority = priorities_spinner.selectedItem.toString()
        val mDescription = description_et.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            // Instert data to database
            val newData = ToDoData(
                0, // 这里的id传0它自己也会自增的
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            // 数据库查看工具：https://sqlitebrowser.org/
            mToDoViewModel.instertData(newData)
            // 返回上一页(listFragment)
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            // 提示用户:请输入标题和内容
            Toast.makeText(requireContext(), getString(R.string.fill_out_all_fields), Toast.LENGTH_SHORT).show()
        }
    }
}
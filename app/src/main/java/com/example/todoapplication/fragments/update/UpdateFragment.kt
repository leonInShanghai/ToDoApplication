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
import com.example.todoapplication.databinding.FragmentUpdateBinding
import com.example.todoapplication.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import android.content.Intent
import androidx.appcompat.app.ActionBar
import com.example.todoapplication.MainActivity


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

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onResume() {
        super.onResume()
        val act = activity as MainActivity
        val actionBar: ActionBar? = act.supportActionBar
        actionBar!!.title = getString(R.string.update)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Data binding
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args

        // Spinner item selected listener
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        // 设置ActionBar右上角的按钮-1
        setHasOptionsMenu(true)

        return binding.root
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
            R.id.munu_share -> shareSoftware() // 用户点击了分享
        }
        return super.onOptionsItemSelected(item)
    }

    // 分享便签到其他应用
    private fun shareSoftware() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val validation = mSharedViewModel.verifyDataFromUser(current_title_et.text.toString(),
            current_description_et.text.toString())

        // 判断分享的便签是否有标题和内容
        if (validation) {
            val msg = current_title_et.text.toString() + "\n" + current_description_et.text.toString()
            intent.putExtra(Intent.EXTRA_TEXT, msg)
            startActivity(Intent.createChooser(intent, "分享到..."))
        } else {
            // 提示用户:请输入标题和内容
            Toast.makeText(requireContext(), getString(R.string.fill_out_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

    // 确认删除？Show alert dialog to confirm removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        val title = if (args.currentItem.title.length > 9) {
            args.currentItem.title.substring(0, 9) + "..."
        } else {
            args.currentItem.title
        }
        builder.setPositiveButton(getString(R.string.yes)) {_,_->
            // 用户点击了确认，执行删除
            mToDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                getString(R.string.successfully_removed, title),
                Toast.LENGTH_SHORT
            ).show()
            // 返回上一页
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(getString(R.string.no)) {_,_-> }
        builder.setTitle(getString(R.string.delete) + " '${title}'?")
        builder.setMessage(getString(R.string.are_you_sure) + " '${title}'?")
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
                description,
                System.currentTimeMillis()
            )
            mToDoViewModel.updateData(updatedItem)
            // Navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            // 提示用户:请输入标题和内容
            Toast.makeText(requireContext(), getString(R.string.fill_out_all_fields), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
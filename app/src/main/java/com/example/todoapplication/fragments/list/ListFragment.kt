package com.example.todoapplication.fragments.list

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
// import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.todoapplication.MainActivity
import com.example.todoapplication.R
import com.example.todoapplication.data.models.ToDoData
import com.example.todoapplication.data.viewmodel.ToDoViewModel
import com.example.todoapplication.databinding.FragmentListBinding
import com.example.todoapplication.fragments.SharedViewModel
import com.example.todoapplication.fragments.list.adapter.ListAdapter
import com.example.todoapplication.fragments.utils.hideKeyboard
import com.example.todoapplication.language.LanguageActivity
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * Created by 公众号：IT波 on 2022/12/4 Copyright © Leon. All rights reserved.
 * Functions: 首页 To-Do-App-Kotlin
 */
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onResume() {
        super.onResume()
        val act = activity as MainActivity
        val actionBar: ActionBar? = act.supportActionBar
        actionBar!!.title = getString(R.string.to_do_list)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        // 实例化recycerView  Setup recyclerview
        setupRecyclerview()

        // Observe LiveData
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        // 点击右下角的“+”按钮跳转到新建页面  databinding代替
        // view.floatingActionButton.setOnClickListener {
              // 点击跳转到添加笔记fragment
        //    findNavController().navigate(R.id.action_listFragment_to_addFragment)
        // }

        // 设置ActionBar右上角的按钮-1
        setHasOptionsMenu(true)

        // 隐藏键盘
        hideKeyboard(requireActivity())

        return binding.root
    }

    // 实例化recycerView  Setup recyclerview
    private fun setupRecyclerview() {
        val recycerView = binding.recyclerView
        recycerView.adapter = adapter
        // LinearLayoutManager(requireActivity()) -> GridLayoutManager(requireContext(), 2)
        // Staggered 交错的
        recycerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // 设置动画
        recycerView.itemAnimator = LandingAnimator().apply {
            addDuration = 300
        }

        // Swipe to delete（左划删除）
        swipeToDelete(recycerView)
    }

    // 左划删除一个item
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object: SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteItem = adapter.dataList[viewHolder.adapterPosition]
                // 数据库删除数据
                mToDoViewModel.deleteItem(deleteItem)
                // 通知适配器删除了一个item（不是因为这句代码itemview消失的）
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                // 用户左划删除后可以反悔 Restore deleted item
                restoreDeletedData(viewHolder.itemView, deleteItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        // 这种左划删除方式不需要刷新adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    // 此处是后悔药用户删除后想要反悔
    private fun restoreDeletedData(view: View, deleteItem: ToDoData) {
        val title = if (deleteItem.title.length > 9) {
            deleteItem.title.substring(0, 9) + "..."
        } else {
            deleteItem.title
        }
        val snackBar = Snackbar.make(
            view,
            getString(R.string.delete) + " '${title}'?",
            Snackbar.LENGTH_LONG
            )
        snackBar.setAction(getString(R.string.undo)) {
            mToDoViewModel.instertData(deleteItem)
            // 此时需要刷新adapter单个item
            // adapter.notifyItemChanged(position) 换成StaggeredGridLayoutManager后注释掉这句，否则左划删除再撤销左划删除会闪退
        }
        snackBar.show()
    }

    // 设置ActionBar右上角的按钮-2
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    // 设置ActionBar右上角的按钮-3 点击事件的监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval() // 再次确认，删除全部数据
            R.id.menu_priority_hight -> mToDoViewModel.sortyByHighPriority.observe(this, Observer {
                // 按高优先级排序
                adapter.setData(it)
            })
            R.id.menu_priority_low -> mToDoViewModel.sortyByLowPriority.observe(this, Observer {
                // 按低优先级排序
                adapter.setData(it)
            })
            R.id.menu_language -> startActivityForResult(Intent(requireContext(), LanguageActivity::class.java),
                101)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == 1) {
                // val act = activity as MainActivity
                // TODO 更新 lable ”全部笔记“
                invalidateOptionsMenu(activity)
                no_data_textView.setText(R.string.no_data)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThoughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThoughDatabase(query)
        }
        return true
    }

    private fun searchThoughDatabase(query: String?) {
        val searchQuery = "%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
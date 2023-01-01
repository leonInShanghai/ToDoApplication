package com.example.todoapplication.language

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import com.example.todoapplication.R
import com.example.todoapplication.databinding.ActivityLanguageBinding
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*

/**
 * Created by 公众号：IT波 on 2022/12/31 Copyright © Leon. All rights reserved.
 * Functions: 语言设置Activity
 * ActionBar设置相关文档 https://www.nhooo.com/note/qagcke.html
 */
class LanguageActivity : AppCompatActivity() {


    private var _binding: ActivityLanguageBinding? = null
    private val binding get() = _binding!!

    private val mViewModel: LanguageViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        // 检查用户上次选择的语言状态
        mViewModel.checkLanguage(rg_parent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_language)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_language)

        binding.vm = mViewModel

        val actionBar: ActionBar? = this.supportActionBar
        actionBar!!.title = getString(R.string.language_setting)
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        mViewModel.updataLanguage.observe(this, androidx.lifecycle.Observer {
            actionBar!!.title = getString(R.string.language_setting)
        })

        // changeLocaleAccordingOptions()
    }

    // 设置ActionBar(左上角返回)点击事件的监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val result = if (mViewModel.isUpdataLanguage()) 1 else 0
            setResult(result)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    fun changeLocaleAccordingOptions() {
        val locale: Locale? = null
        val resources = getResources();//获得res资源对象
        val config = resources.getConfiguration();//获得设置对象
        val dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率像素等。
        config.locale = Locale.KOREA;
        resources.updateConfiguration(config,dm);
    }

}
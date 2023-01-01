package com.example.todoapplication.language

import android.app.Application
import android.content.Context
import android.widget.RadioGroup
import androidx.lifecycle.AndroidViewModel
import com.example.todoapplication.fragments.utils.SharedPreferencesUtils
import com.example.todoapplication.R
import android.content.res.Configuration;
import android.os.Build
import android.os.LocaleList
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*
import kotlin.contracts.Returns


const val LANGUAGE_KEY: String = "LANGUAGE_KEY"

/**
 * Created by 公众号：IT波 on 2022/12/31 Copyright © Leon. All rights reserved.
 * Functions: 语言设置viewModel
 */
class LanguageViewModel(private val application: Application): AndroidViewModel(application) {


    val updataLanguage: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkLanguage(rg: RadioGroup) {
        val value = SharedPreferencesUtils.getParam(getApplication(), LANGUAGE_KEY, 0)
        when (value) {
            0 -> rg.check(R.id.radioButton)
            R.id.radioButton -> rg.check(R.id.radioButton)

            R.id.radioButton2 -> rg.check(R.id.radioButton2)
            R.id.radioButton3 -> rg.check(R.id.radioButton3)
            R.id.radioButton4 -> rg.check(R.id.radioButton4)
        }
    }

    fun onCustomCheckChanged(radio: RadioGroup?, id: Int) {
        // 本地持久化保存用户的选择 跟随系统  中文 英文 朝鲜文
        SharedPreferencesUtils.setParam(getApplication(), LANGUAGE_KEY, id)

        changeLocaleAccordingOptions(radio!!.context, id)
        val radioBtn = radio.getChildAt(0) as RadioButton
        radioBtn.setText(radio.context.getString(R.string.follow_system))
        updataLanguage.value = true
    }


    /**
     * 调用安卓国际化的方法
     * @param id 代表用户点击的RadioButton
     */
    fun changeLocaleAccordingOptions(context: Context, id: Int) {

        val resources = context.getResources() // 获得res资源对象
        val config = resources.getConfiguration() // 获得设置对象
        val dm = resources.getDisplayMetrics() // 获得屏幕参数：主要是分辨率像素等。

        when (id) {
            R.id.radioButton -> config.locale = getSystemLocale()
            R.id.radioButton2 -> config.locale = Locale.CHINESE // 使用中文
            R.id.radioButton3 -> config.locale = Locale.ENGLISH
            R.id.radioButton4 -> config.locale = Locale.KOREA
        }
//        val config = Configuration()
//        config.locale = locale
//        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics())
        resources.updateConfiguration(config, dm)
    }

    fun isUpdataLanguage(): Boolean {
        return updataLanguage.value == true
    }

    /**
     * 获取当前系统语言，如未包含则默认英文
     */
    private fun getSystemLocale(): Locale {
        // val systemLocale = Locale.getDefault().getDisplayLanguage();
        val locale = application.getResources().getConfiguration().locale
        Log.d("systemLocale", locale.getLanguage());

        if (locale.getLanguage().startsWith("en")) {
            return Locale.ENGLISH
        } else if (locale.getLanguage().startsWith("zh")) {
            return Locale.CHINESE
        } else if (locale.getLanguage().startsWith("ko")) {
            return Locale.KOREA
        } else {
            return Locale.CHINESE
        }
        return Locale.CHINESE
    }
}
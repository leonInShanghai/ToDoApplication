package com.example.todoapplication

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.todoapplication.fragments.utils.SharedPreferencesUtils
import com.example.todoapplication.language.LANGUAGE_KEY
import java.util.*

/**
 * Created by 公众号：IT波 on 2022/12/31 Copyright © Leon. All rights reserved.
 * Functions:
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        updateConfiguration(this)
    }

    private fun updateConfiguration(context: Context) {
        val value = SharedPreferencesUtils.getParam(context, LANGUAGE_KEY, 0)

        val resources = context.getResources() // 获得res资源对象
        val config = resources.getConfiguration() // 获得设置对象
        val dm = resources.getDisplayMetrics() // 获得屏幕参数：主要是分辨率像素等。

        Log.d("555", "" + value)

        when (value) {
            0 -> config.locale = getSystemLocale()
            R.id.radioButton -> config.locale = getSystemLocale()

            R.id.radioButton2 -> {
                Log.d("555", "222")
                // 使用中文
                config.locale = Locale.CHINESE
            }
            R.id.radioButton3 -> {
                Log.d("555", "333")
                config.locale = Locale.ENGLISH
            }
            R.id.radioButton4 -> {
                Log.d("555", "444")
                config.locale = Locale.KOREA
            }
        }
//        val config = Configuration()
//        config.locale = locale
//        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics())
        resources.updateConfiguration(config, dm)
    }

    /**
     * 获取当前系统语言，如未包含则默认英文
     */
    private fun getSystemLocale(): Locale {
//        val systemLocale = Locale.getDefault().getDisplayLanguage();
//        Log.d("systemLocale", systemLocale);
//
//        if (systemLocale.startsWith("English")) {
//            return Locale.ENGLISH
//        } else if (systemLocale.startsWith("中文")) {
//            return Locale.CHINESE
//        } else if (systemLocale.startsWith("한국어")) {
//            return Locale.KOREA
//        } else {
//            return Locale.CHINESE
//        }
        val locale = this.getResources().getConfiguration().locale
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
    }
}
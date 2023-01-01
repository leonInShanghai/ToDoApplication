package com.example.todoapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todoapplication.fragments.utils.SharedPreferencesUtils
import com.example.todoapplication.language.LANGUAGE_KEY
import java.util.*

/**
 * 数据库查看工具：https://sqlitebrowser.org/
 * Anything difficult is usually worth the effort.
 * No pain, no gain.
 * That's right! Go for it!
 * Created by 公众号：IT波 on 2022/12/10 Copyright © Leon. All rights reserved.
 * Functions:
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateConfiguration()

        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun updateConfiguration() {
        val value = SharedPreferencesUtils.getParam(this.applicationContext, LANGUAGE_KEY, 0)

        val resources = getResources() // 获得res资源对象
        val config = resources.getConfiguration() // 获得设置对象
        val dm = resources.getDisplayMetrics() // 获得屏幕参数：主要是分辨率像素等。

        Log.d("MainActivity", "" + value)

        when (value) {
            0 -> config.locale = getSystemLocale()
            R.id.radioButton -> config.locale = getSystemLocale()

            R.id.radioButton2 -> {
                Log.d("MainActivity", "222")
                // 使用中文
                config.locale = Locale.CHINESE
            }
            R.id.radioButton3 -> {
                Log.d("MainActivity", "333")
                config.locale = Locale.ENGLISH
            }
            R.id.radioButton4 -> {
                Log.d("MainActivity", "444")
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

        val locale = getResources().getConfiguration().locale
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
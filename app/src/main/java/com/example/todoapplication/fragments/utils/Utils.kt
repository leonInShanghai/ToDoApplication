package com.example.todoapplication.fragments.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

/**
 * Created by 公众号：IT波 on 2022/12/25 Copyright © Leon. All rights reserved.
 * Functions: 控制键盘的隐藏工具类
 */


fun hideKeyboard(activity: Activity) {
    val inputMethManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
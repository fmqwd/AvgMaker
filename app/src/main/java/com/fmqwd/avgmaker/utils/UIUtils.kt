package com.fmqwd.avgmaker.utils

import android.content.Context
import android.widget.Toast

/**
 * @projectName
 * @author :fmqwd
 * @description: UI相关工具类
 * @date :2023/10/28 11:36
 */
object UIUtils {
    fun dip2px(context: Context, dpValue: Float): Int =
        (dpValue * context.resources.displayMetrics.density + 0.5).toInt()

    fun px2dip(context: Context, pxValue: Float): Int =
        (pxValue / context.resources.displayMetrics.density + 0.5).toInt()

    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun toast(context: Context, text: String, isLong: Boolean) {
        if (isLong) Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
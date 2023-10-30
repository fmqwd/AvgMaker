package com.fmqwd.avgmaker.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings

object FileUtils {
    fun checkFilePermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            return false
        }
        return true
    }

    fun getFilePermissions(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !checkFilePermission()) {
            AlertDialog.Builder(context).setTitle("加载外部文件需要提供访问权限")
                .setPositiveButton("取消", null).setNegativeButton(
                    "确定"
                ) { _, _ ->
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.fromParts("package", context.packageName, null)
                    context.startActivity(intent)
                }.create().show()
        }
    }


}

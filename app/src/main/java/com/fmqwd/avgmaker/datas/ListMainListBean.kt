package com.fmqwd.avgmaker.datas

import com.fmqwd.avgmaker.database.MainListData

/*
 * @author :fmqwd
 * @projectName
 * @description: 首页使用的bean类
 * @date :2023/10/28 10:21
 */


data class ListMainListBean(
    val id: String,
    var title: String,
    var name: String,
    var path: String,
    var image: String
) {
    fun toMainListData(): MainListData {
        return MainListData(id, title, name, image, path)
    }
}

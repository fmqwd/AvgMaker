package com.fmqwd.avgmaker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fmqwd.avgmaker.datas.ListMainListBean

/**
 * @projectName
 * @author :fmqwd
 * @description:
 * @date :2023/10/29 11:48
 */
@Entity(tableName = "MainListData")
data class MainListData(
    @PrimaryKey val id: String,
    val title: String,
    val name: String,
    val path: String,
    val image: String
) {
    fun toListMainListBean(): ListMainListBean {
        return ListMainListBean(id, title, name, image, path)
    }

}

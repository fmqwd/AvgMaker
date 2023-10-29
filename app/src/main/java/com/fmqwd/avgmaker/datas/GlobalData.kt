package com.fmqwd.avgmaker.datas

/*
 * @author :fmqwd
 * @projectName
 * @description: 全局变量类，维护并存储一些需要的全局变量
 * @date :2023/10/28 10:21
 */
object GlobalData {
    @Volatile
    private var mainListData: ArrayList<ListMainListBean>? = null

    fun getMainListData(): ArrayList<ListMainListBean>? {
        return mainListData?.let { return it }
    }

    fun setMainListData(mainListData: ArrayList<ListMainListBean>) {
        this.mainListData = mainListData
    }

    fun addIntoMainList(list: ListMainListBean) {
        if (mainListData != null) {
            mainListData?.add(list)
        }
    }

    fun removeIntoMainList(list: ListMainListBean) {
        if (mainListData != null) {
            mainListData?.remove(list)
        }
    }
}
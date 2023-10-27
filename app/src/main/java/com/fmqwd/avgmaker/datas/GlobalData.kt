package com.fmqwd.avgmaker.datas

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
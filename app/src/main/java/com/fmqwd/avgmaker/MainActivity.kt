package com.fmqwd.avgmaker

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fmqwd.avgmaker.adapters.ListMainListAdapter
import com.fmqwd.avgmaker.databinding.ActivityMainBinding
import com.fmqwd.avgmaker.datas.ListMainListBean
import com.fmqwd.avgmaker.utils.UIUtils.dip2px

/*
 * @author :fmqwd
 * @projectName
 * @description: 首页
 * @date :2023/10/28 10:21
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initThemes()
        initTitle()
        initListView()

    }

    private fun initThemes() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    }

    private fun initTitle() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val popupView = inflater.inflate(R.layout.dialog_popuowindow_main_add, binding.root, false)
        val popupWindow =
            PopupWindow(popupView, dip2px(this, 130f), ViewGroup.LayoutParams.WRAP_CONTENT)

        popupWindow.isOutsideTouchable = true

        binding.mainSearch.setOnClickListener {

        }
        binding.mainAdd.setOnClickListener {
            popupWindow.showAsDropDown(binding.mainAdd, -dip2px(this, 100f), 0)
            popupWindow.isOutsideTouchable = true
            popupView!!.findViewById<TextView>(R.id.dialog_native_import).setOnClickListener {
                popupWindow.dismiss()
            }
            popupView.findViewById<TextView>(R.id.dialog_load_package).setOnClickListener {
                popupWindow.dismiss()
            }
            popupView.findViewById<TextView>(R.id.dialog_my_game).setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }

    private fun initListView() {
        val list = ArrayList<ListMainListBean>()
        for (i in 1 until  100)list.add(ListMainListBean("111", "111$i", "111", ",", "111"))
        val listAdapter = ListMainListAdapter(this,list)
        binding.mainList.adapter = listAdapter
    }
}

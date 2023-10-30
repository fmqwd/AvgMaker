package com.fmqwd.avgmaker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.fmqwd.avgmaker.adapters.ListMainListAdapter
import com.fmqwd.avgmaker.database.DatabaseManager
import com.fmqwd.avgmaker.database.MainListData
import com.fmqwd.avgmaker.databinding.ActivityMainBinding
import com.fmqwd.avgmaker.datas.GlobalData
import com.fmqwd.avgmaker.datas.ListMainListBean
import com.fmqwd.avgmaker.pages.GameCreatePage.GameCreatePage
import com.fmqwd.avgmaker.utils.FileUtils
import com.fmqwd.avgmaker.utils.UIUtils.dip2px
import com.fmqwd.avgmaker.utils.UIUtils.toast
import com.fmqwd.avgmaker.utils.ZipUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


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

    override fun onResume() {
        super.onResume()
        updateListView()
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

        binding.mainNoneDataAdd.setOnClickListener {
            getProjectFromZip()
        }

        binding.mainSearch.setOnClickListener {

        }
        binding.mainAdd.setOnClickListener {
            popupWindow.showAsDropDown(binding.mainAdd, -dip2px(this, 100f), 0)
            popupWindow.isOutsideTouchable = true
            popupView!!.findViewById<TextView>(R.id.dialog_create_game).setOnClickListener {
                val intent = Intent(this@MainActivity,GameCreatePage::class.java)
                startActivity(intent)
                popupWindow.dismiss()
            }
            popupView.findViewById<TextView>(R.id.dialog_load_package).setOnClickListener {
                getProjectFromZip()
                popupWindow.dismiss()
            }
            popupView.findViewById<TextView>(R.id.dialog_my_game).setOnClickListener {
                popupWindow.dismiss()
            }
        }
    }

    private fun initListView() {
        CoroutineScope(Dispatchers.Default).launch {
            val database = DatabaseManager.getDatabase()
            val mainListDao = database.mainListDataDao()
            val dbList: List<MainListData> = mainListDao.getAll()

            if (dbList.isEmpty()) {
                binding.mainList.visibility = View.INVISIBLE
                binding.mainNoneDataAdd.visibility = View.VISIBLE
            } else {
                val adapterList: List<ListMainListBean> = dbList.map { it.toListMainListBean() }
                val listAdapter = ListMainListAdapter(this@MainActivity, ArrayList(adapterList))
                binding.mainList.adapter = listAdapter
            }
        }
    }

    private fun updateListView() {
        if (GlobalData.getMainListData().isNullOrEmpty()) {
            binding.mainList.visibility = View.INVISIBLE
            binding.mainNoneDataAdd.visibility = View.VISIBLE
        } else {
            binding.mainNoneDataAdd.visibility = View.INVISIBLE
            val listAdapter = GlobalData.getMainListData()?.let { ListMainListAdapter(this, it) }
            binding.mainList.adapter = listAdapter
        }
    }

    private fun getProjectFromZip() {
        if (!FileUtils.checkFilePermission()) {
            FileUtils.getFilePermissions(this@MainActivity)
        } else {
            filePickerLauncher.launch("application/zip")
        }
    }

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val selectedFilePath = uri?.let { getRealPathFromUri(it) }
            if (selectedFilePath != null) {
                val destinationPath = filesDir.path + File.separator +"project"
                val zipUtils = ZipUtils()
                zipUtils.unzip(selectedFilePath, destinationPath, object : ZipUtils.OnProgressListener {
                    override fun onProgress(progress: Int) {
                      println("解压中，解压进度：$progress%……")
                    }
                })
            } else {
                toast(this@MainActivity, "文件路径错误，请直接选择文件管理中的文件")
            }
        }

    private fun getRealPathFromUri(uri: Uri): String? {
        val contentResolver = contentResolver
        val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor?.moveToFirst()
        val path = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return path
    }

}

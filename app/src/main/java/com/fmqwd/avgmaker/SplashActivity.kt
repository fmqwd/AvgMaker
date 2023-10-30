package com.fmqwd.avgmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fmqwd.avgmaker.database.DatabaseManager
import com.fmqwd.avgmaker.database.MainListData
import com.fmqwd.avgmaker.datas.GlobalData
import com.fmqwd.avgmaker.datas.ListMainListBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
 * @author :fmqwd
 * @projectName
 * @description: Splash页，包括全局初始化相关内容
 * @date :2023/10/28 10:21
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val countDownTime = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {

            //初始化相关放在这里
            initialize()

            //首页计时时间
            delay(countDownTime)
            jump2MainPage()
        }
    }

    //初始化放相关在这里
    private fun initialize() {
        DatabaseManager.initialize(this)

        val database = DatabaseManager.getDatabase()
        val mainListDao = database.mainListDataDao()
        val dbList: List<MainListData> = mainListDao.getAll()
        val adapterList: List<ListMainListBean> = dbList.map { it.toListMainListBean() }

        GlobalData.setMainListData(ArrayList(adapterList))
    }

    private fun jump2MainPage() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
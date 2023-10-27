package com.fmqwd.avgmaker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fmqwd.avgmaker.datas.GlobalData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    private suspend fun initialize() {
        // TODO:
    }

    private fun jump2MainPage() {
        intent.setClass(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
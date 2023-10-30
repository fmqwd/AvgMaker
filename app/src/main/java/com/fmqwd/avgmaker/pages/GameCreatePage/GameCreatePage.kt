package com.fmqwd.avgmaker.pages.GameCreatePage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fmqwd.avgmaker.R
import com.fmqwd.avgmaker.databinding.ActivityGameCreatePageBinding

class GameCreatePage : AppCompatActivity() {
    private lateinit var binding: ActivityGameCreatePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameCreatePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolBar()
    }

    private fun initToolBar() {
        setSupportActionBar(binding.gameCreateToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "新建作品"
        binding.gameCreateToolbar.setNavigationOnClickListener { finish() }
        binding.gameCreateToolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText)
        binding.gameCreateToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.gameCreateToolbarTextView.text = "保存"
        binding.gameCreateToolbarTextView.setOnClickListener {
            // TODO:
        }

    }
}
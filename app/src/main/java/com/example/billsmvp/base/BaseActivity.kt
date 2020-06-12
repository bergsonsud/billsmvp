package com.example.billsmvp.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.billsmvp.R

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOrientation()
    }

    private fun setupOrientation() {
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }
}
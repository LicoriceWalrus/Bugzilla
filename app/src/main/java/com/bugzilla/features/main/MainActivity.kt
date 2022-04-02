package com.bugzilla.features.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.bugzilla.R
import com.bugzilla.features.bugs.presentations.list.ScreenBugList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            ScreenBugList()
        }
    }
}
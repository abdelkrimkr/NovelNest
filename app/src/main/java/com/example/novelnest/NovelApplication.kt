package com.example.novelnest

import android.app.Application
import com.example.novelnest.data.AppContainer
import com.example.novelnest.data.DefaultContainer

class NovelApplication : Application(){

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer()
    }
}
package ru.yandex.dunaev.mick.myrecyclerswipeanddrug

import android.app.Application

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        Repository.init(applicationContext)
    }
}
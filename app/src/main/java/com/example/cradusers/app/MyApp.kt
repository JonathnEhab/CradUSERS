package com.example.cradusers.app
import android.app.Application
import com.example.data.local.UserInfoDatabase

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        UserInfoDatabase.getInstance(this)
    }
}
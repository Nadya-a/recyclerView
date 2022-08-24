package com.example.recuclerview

import android.app.Application
import com.example.recuclerview.model.UsersService

class App : Application() {
    lateinit var usersService: UsersService
    override fun onCreate() {
        super.onCreate()
        usersService = UsersService(applicationContext)
    }


}
package com.example.psnotes

import android.app.Application
import android.util.Log
import com.example.psnotes.data.AppDatabase
import com.example.psnotes.data.AppDatabaseSingleton
import com.example.psnotes.ui.viewmodel.PermissionViewModel
import javax.inject.Singleton

class PsNotes : Application() {
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        database = AppDatabaseSingleton.getDatabase(this)
    }
}
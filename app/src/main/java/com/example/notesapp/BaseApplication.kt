package com.example.notesapp

import android.app.Application
import com.example.notesapp.data.db.NotesDatabase
import com.example.notesapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // koin setup
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                appModule
            ) // Register your DI modules
        }
    }
}
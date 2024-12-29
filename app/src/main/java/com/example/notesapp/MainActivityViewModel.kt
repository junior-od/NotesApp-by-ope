package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.notesapp.data.workers.autosync.AutoSyncWorker
import com.example.notesapp.data.workers.user.UserUploadWorker
import com.example.notesapp.domain.user.UserLoggedInUseCase
import com.example.notesapp.domain.workers.SyncDataUseCase
import java.util.concurrent.TimeUnit

/**
 * MainActivityViewModel to act
 * as a middle man communication between
 * ui and data needed for the main activity
 * */
class MainActivityViewModel(
    private val userLoggedInUseCase: UserLoggedInUseCase,
    private val syncDataUseCase: SyncDataUseCase
): ViewModel() {

    /**
     * is user logged in
     * */
    fun isUserLoggedIn(): Boolean{
        return userLoggedInUseCase()
    }

    /**
     * Sync app tables data
     * */
    fun syncData(){
        syncDataUseCase()
    }

}
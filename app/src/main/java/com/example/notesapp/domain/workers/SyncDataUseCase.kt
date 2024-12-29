package com.example.notesapp.domain.workers

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.notesapp.data.workers.autosync.AutoSyncWorker
import java.util.concurrent.TimeUnit

/**
 * SyncData UseCase
 * */
class SyncDataUseCase(
    private val workManager: WorkManager
) {

    operator fun invoke(){
        val appSync = PeriodicWorkRequestBuilder<AutoSyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresDeviceIdle(false).build()
            ).build()
        workManager.enqueueUniquePeriodicWork(
            "auto_sync",
            ExistingPeriodicWorkPolicy.KEEP, appSync
        )
    }
}
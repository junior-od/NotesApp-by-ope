package com.example.notesapp.data.workers.autosync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.await
import kotlinx.coroutines.delay

/**
 * Auto sync worker to run all workers
 * on specified periodic interval
 * */
class AutoSyncWorker(
    private val context: Context,
    private val workParam: WorkerParameters,
    private val workManager: WorkManager
): CoroutineWorker(context, workParam)  {
    override suspend fun doWork(): Result {
        return try {

            // run sync up of all tables first
            SyncUp.syncUp(workManager)
            delay(3000L) // 3 seconds delay before initiating download sync
            syncDownProceed(
                context = context,
                workManager = workManager
            )
            Result.success()
        } catch (e: Exception) {
            Result.success()
        }
    }

    /**
     * recursive function to check
     * if sync up is complete
     * before initiating sync down
     * */
    private suspend fun syncDownProceed(context: Context, workManager: WorkManager){
        val listenableWorker = workManager.getWorkInfosForUniqueWork(SyncUp.UNIQUE_WORK_SYNC_UP)
        val workInfo = listenableWorker.await()
        val isAnyWorkerRunning = workInfo.any { it.state == WorkInfo.State.RUNNING }
        if(workInfo != null && !isAnyWorkerRunning){
            // initiate sync down
            SyncDown.syncDown(workManager)
        } else {
            delay(3000L)
            syncDownProceed(context,workManager)
        }
    }

}
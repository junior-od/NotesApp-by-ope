package com.example.notesapp.data.workers.notecategory

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.notecategory.NoteCategoryRepo

class NoteCategoryUploadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteCategoryRepo: NoteCategoryRepo
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        Log.d("yeah", "note category upload worker reaches")

        return Result.success()
    }

}
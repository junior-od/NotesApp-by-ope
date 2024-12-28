package com.example.notesapp.data.workers.todos

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.todos.NoteTodoRepo

class TodosDownloadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteTodoRepo: NoteTodoRepo
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        Log.d("yeah", "note todo download worker reaches")

        return Result.success()
    }
}
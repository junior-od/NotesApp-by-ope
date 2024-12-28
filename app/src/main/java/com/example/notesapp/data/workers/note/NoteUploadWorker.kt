package com.example.notesapp.data.workers.note

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.note.NoteRepo

/**
 * Note UploadWorker
 * */
class NoteUploadWorker(context: Context,
                       private val workParam: WorkerParameters,
                       private val noteRepo: NoteRepo
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        Log.d("yeah", "note upload worker reaches")

        return Result.success()
    }

}
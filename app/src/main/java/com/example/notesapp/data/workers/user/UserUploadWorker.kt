package com.example.notesapp.data.workers.user

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.user.UserRepo

/**
 * User UploadWorker
 * */
class UserUploadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val userRepo: UserRepo
): CoroutineWorker(context, workParam)  {


    override suspend fun doWork(): Result {
        Log.d("yeah", "user upload worker reaches${userRepo.getSignedInUserId()}")

        return Result.success()
    }
}
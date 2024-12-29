package com.example.notesapp.data.workers.user

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.user.User
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.data.user.UserTable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * User DownloadWorker
 * */
class UserDownloadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val userRepo: UserRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {

        firestore.collection(UserTable.TABLE_NAME)
            .get()
            .addOnSuccessListener {
                documents ->
                CoroutineScope(Dispatchers.IO).launch{
                    documents.forEach {
                        userRepo.insertUser(it.toUserEntity())
                    }
                }
            }
            .addOnFailureListener {
                    e->
                Log.d("failure-yeah user download",e.message.toString())
            }

        Log.d("yeah", "user download worker reaches${userRepo.getSignedInUserId()}")

        return Result.success()
    }

    /**
     * convert snapshot to user entity
     * */
    private fun QueryDocumentSnapshot.toUserEntity(): User{
        val data = this.data
        return User(
            id = data[UserTable.ID] as String,
            firstName = data[UserTable.FIRST_NAME] as String?,
            lastName = data[UserTable.LAST_NAME] as String?,
            email = data[UserTable.EMAIL] as String?,
            signUpMethod = data[UserTable.SIGN_UP_METHOD] as String?,
            createdAt = data[UserTable.CREATED_AT] as String?,
            updatedAt = data[UserTable.UPDATED_AT] as String?,
            syncFlag = 1,
        )
    }

}
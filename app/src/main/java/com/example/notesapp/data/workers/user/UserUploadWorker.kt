package com.example.notesapp.data.workers.user

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.user.User
import com.example.notesapp.data.user.UserRepo
import com.example.notesapp.data.user.UserTable
import com.example.notesapp.utils.DateTimeUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * User UploadWorker
 * */
class UserUploadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val userRepo: UserRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam)  {

    override suspend fun doWork(): Result {
        // get user record to upload
        val users = withContext(Dispatchers.IO){userRepo.getAllUsersDataToUpload()}

        // date to be saved as updated as
        val updatedAtToBeSaved = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()

        users.forEach {
            user ->

            firestore.collection(UserTable.TABLE_NAME)
                .document(user.id)
                .set(user.hashMapped(updatedAtToBeSaved))
                .addOnSuccessListener {
                    result ->

                    CoroutineScope(Dispatchers.IO).launch{
                        val updatedUser = user.copy(
                            updatedAt = updatedAtToBeSaved,
                            syncFlag = 1
                        )
                        userRepo.insertUser(updatedUser)
                    }

                }
                .addOnFailureListener {
                    e->
                    Log.d("failure-yeah user upload",e.message.toString())
                }
        }


        Log.d("yeah", "user upload worker reaches${userRepo.getSignedInUserId()}")

        return Result.success()
    }

    /**
     * convert user model to hash map
     * */
    private fun User.hashMapped(updateAt: String): HashMap<String, String?> {
        return hashMapOf(
            UserTable.ID to this.id,
            UserTable.EMAIL to this.email,
            UserTable.FIRST_NAME to this.firstName,
            UserTable.LAST_NAME to this.lastName,
            UserTable.SIGN_UP_METHOD to this.signUpMethod,
            UserTable.CREATED_AT to this.createdAt,
            UserTable.UPDATED_AT to updateAt
        )
    }
}
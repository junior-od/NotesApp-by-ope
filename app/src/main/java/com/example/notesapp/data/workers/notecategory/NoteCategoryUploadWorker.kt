package com.example.notesapp.data.workers.notecategory

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryRepo
import com.example.notesapp.data.notecategory.NoteCategoryTable
import com.example.notesapp.utils.DateTimeUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteCategoryUploadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteCategoryRepo: NoteCategoryRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {

        // get note category record to upload
        val noteCategorys = withContext(Dispatchers.IO){noteCategoryRepo.getAllNoteCategoryToUpload()}

        // date to be saved as updated as
        val updatedAtToBeSaved = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()

        noteCategorys.forEach {
                noteCatergory ->

            firestore.collection(NoteCategoryTable.TABLE_NAME)
                .document(noteCatergory.id)
                .set(noteCatergory.hashMapped(updatedAtToBeSaved))
                .addOnSuccessListener {
                        result ->

                    CoroutineScope(Dispatchers.IO).launch{
                        val updatedNoteCategory = noteCatergory.copy(
                            updatedAt = updatedAtToBeSaved,
                            syncFlag = 1
                        )
                        noteCategoryRepo.insertNoteCategory(updatedNoteCategory)
                    }

                }
                .addOnFailureListener {
                        e->
                    Log.d("failure-yeah note category upload",e.message.toString())
                }
        }

        Log.d("yeah", "note category upload worker reaches")

        return Result.success()
    }

    /**
     * convert note category model to hash map
     * */
    private fun NoteCategory.hashMapped(updateAt: String): HashMap<String, Any?> {
        return hashMapOf(
            NoteCategoryTable.ID to this.id,
            NoteCategoryTable.NAME to this.name,
            NoteCategoryTable.CREATED_BY to this.createdBy,
            NoteCategoryTable.CREATED_AT to this.createdAt,
            NoteCategoryTable.UPDATED_AT to updateAt
        )
    }

}
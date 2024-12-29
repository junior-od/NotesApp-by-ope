package com.example.notesapp.data.workers.notecategory

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryRepo
import com.example.notesapp.data.notecategory.NoteCategoryTable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteCategoryDownloadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteCategoryRepo: NoteCategoryRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        firestore.collection(NoteCategoryTable.TABLE_NAME)
            .get()
            .addOnSuccessListener {
                    documents ->
                CoroutineScope(Dispatchers.IO).launch{
                    documents.forEach {
                        noteCategoryRepo.insertNoteCategory(it.toNoteCategoryEntity())
                    }
                }
            }
            .addOnFailureListener {
                    e->
                Log.d("failure-yeah note todo download",e.message.toString())
            }
        Log.d("yeah", "note category download worker reaches")

        return Result.success()
    }

    /**
     * convert snapshot to note category entity
     * */
    private fun QueryDocumentSnapshot.toNoteCategoryEntity(): NoteCategory {
        val data = this.data
        return NoteCategory(
            id = data[NoteCategoryTable.ID] as String,
            name = data[NoteCategoryTable.NAME] as String?,
            createdBy = data[NoteCategoryTable.CREATED_BY] as String?,
            createdAt = data[NoteCategoryTable.CREATED_AT] as String?,
            updatedAt = data[NoteCategoryTable.UPDATED_AT] as String?,
            syncFlag = 1,
        )
    }
}
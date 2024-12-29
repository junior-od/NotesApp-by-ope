package com.example.notesapp.data.workers.todos

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.data.todos.NoteTodoRepo
import com.example.notesapp.data.todos.NoteTodoTable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodosDownloadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteTodoRepo: NoteTodoRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        firestore.collection(NoteTodoTable.TABLE_NAME)
            .get()
            .addOnSuccessListener {
                    documents ->
                CoroutineScope(Dispatchers.IO).launch{
                    documents.forEach {
                        noteTodoRepo.insertNoteTodo(it.toNoteTodoEntity())
                    }
                }
            }
            .addOnFailureListener {
                    e->
                Log.d("failure-yeah note todo download",e.message.toString())
            }
        Log.d("yeah", "note todo download worker reaches")

        return Result.success()
    }

    /**
     * convert snapshot to to-do entity
     * */
    private fun QueryDocumentSnapshot.toNoteTodoEntity(): NoteTodo {
        val data = this.data
        return NoteTodo(
            id = data[NoteTodoTable.ID] as String,
            noteId = data[NoteTodoTable.NOTE_ID] as String?,
            todo = data[NoteTodoTable.TODO] as String?,
            todoCompleted = (data[NoteTodoTable.TODO_COMPLETED] as Long).toInt(),
            deleteFlag = (data[NoteTodoTable.DELETE_FLAG] as Long).toInt(),
            createdBy = data[NoteTodoTable.CREATED_BY] as String?,
            createdAt = data[NoteTodoTable.CREATED_AT] as String?,
            updatedAt = data[NoteTodoTable.UPDATED_AT] as String?,
            syncFlag = 1,
        )
    }
}
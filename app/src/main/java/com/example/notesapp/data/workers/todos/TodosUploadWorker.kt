package com.example.notesapp.data.workers.todos

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.notecategory.NoteCategoryTable
import com.example.notesapp.data.todos.NoteTodo
import com.example.notesapp.data.todos.NoteTodoRepo
import com.example.notesapp.data.todos.NoteTodoTable
import com.example.notesapp.utils.DateTimeUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodosUploadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteTodoRepo: NoteTodoRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {

        // get note to-do record to upload
        val noteTodos = withContext(Dispatchers.IO){noteTodoRepo.getAllNoteTodoToUpload()}

        // date to be saved as updated as
        val updatedAtToBeSaved = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()

        noteTodos.forEach {
                noteTodo ->

            firestore.collection(NoteTodoTable.TABLE_NAME)
                .document(noteTodo.id)
                .set(noteTodo.hashMapped(updatedAtToBeSaved))
                .addOnSuccessListener {
                        result ->

                    CoroutineScope(Dispatchers.IO).launch{
                        val updatedNoteTodo = noteTodo.copy(
                            updatedAt = updatedAtToBeSaved,
                            syncFlag = 1
                        )
                        noteTodoRepo.insertNoteTodo(updatedNoteTodo)
                    }

                }
                .addOnFailureListener {
                        e->
                    Log.d("failure-yeah note todo upload",e.message.toString())
                }
        }

        Log.d("yeah", "note todo upload worker reaches")

        return Result.success()
    }

    /**
     * convert note to-do model to hash map
     * */
    private fun NoteTodo.hashMapped(updateAt: String): HashMap<String, Any?> {
        return hashMapOf(
            NoteTodoTable.ID to this.id,
            NoteTodoTable.NOTE_ID to this.noteId,
            NoteTodoTable.TODO to this.todo,
            NoteTodoTable.TODO_COMPLETED to this.todoCompleted,
            NoteTodoTable.DELETE_FLAG to this.deleteFlag,
            NoteTodoTable.CREATED_BY to this.createdBy,
            NoteTodoTable.CREATED_AT to this.createdAt,
            NoteTodoTable.UPDATED_AT to updateAt
        )
    }
}
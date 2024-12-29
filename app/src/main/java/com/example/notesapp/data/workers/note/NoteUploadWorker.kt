package com.example.notesapp.data.workers.note

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.data.note.NoteTable
import com.example.notesapp.data.user.User
import com.example.notesapp.data.user.UserTable
import com.example.notesapp.utils.DateTimeUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Note UploadWorker
 * */
class NoteUploadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteRepo: NoteRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {
    override suspend fun doWork(): Result {
        // get note record to upload
        val notes = withContext(Dispatchers.IO){noteRepo.getAllNotesToUpload()}

        // date to be saved as updated as
        val updatedAtToBeSaved = DateTimeUtils.getCurrentDateTimeInFullDateTimeFormat()

        notes.forEach {
                note ->

            firestore.collection(NoteTable.TABLE_NAME)
                .document(note.id)
                .set(note.hashMapped(updatedAtToBeSaved))
                .addOnSuccessListener {
                        result ->

                    CoroutineScope(Dispatchers.IO).launch{
                        val updatedNote = note.copy(
                            updatedAt = updatedAtToBeSaved,
                            syncFlag = 1
                        )
                        noteRepo.insertNote(updatedNote)
                    }

                }
                .addOnFailureListener {
                        e->
                    Log.d("failure-yeah note upload",e.message.toString())
                }
        }

        Log.d("yeah", "note upload worker reaches")

        return Result.success()
    }

    /**
     * convert note model to hash map
     * */
    private fun Note.hashMapped(updateAt: String): HashMap<String, Any?> {
        return hashMapOf(
            NoteTable.ID to this.id,
            NoteTable.NOTE_TITLE to this.noteTitle,
            NoteTable.NOTE_CATEGORY_ID to this.noteCategoryId,
            NoteTable.NOTE_INFO to this.noteInfo,
            NoteTable.DELETE_FLAG to this.deleteFlag,
            NoteTable.CREATED_BY to this.createdBy,
            NoteTable.CREATED_AT to this.createdAt,
            NoteTable.UPDATED_AT to updateAt
        )
    }

}
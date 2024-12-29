package com.example.notesapp.data.workers.note

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.notesapp.data.note.Note
import com.example.notesapp.data.note.NoteRepo
import com.example.notesapp.data.note.NoteTable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Note DownloadWorker
 * */
class NoteDownloadWorker(
    context: Context,
    private val workParam: WorkerParameters,
    private val noteRepo: NoteRepo,
    private val firestore: FirebaseFirestore
): CoroutineWorker(context, workParam) {

    override suspend fun doWork(): Result {

        firestore.collection(NoteTable.TABLE_NAME)
            .get()
            .addOnSuccessListener {
                    documents ->
                CoroutineScope(Dispatchers.IO).launch{
                    documents.forEach {
                        noteRepo.insertNote(it.toNoteEntity())
                    }
                }
            }
            .addOnFailureListener {
                    e->
                Log.d("failure-yeah note todo download",e.message.toString())
            }

        Log.d("yeah", "note download worker reaches")

        return Result.success()
    }

    /**
     * convert snapshot to note entity
     * */
    private fun QueryDocumentSnapshot.toNoteEntity(): Note {
        val data = this.data
        return Note(
            id = data[NoteTable.ID] as String,
            noteCategoryId = data[NoteTable.NOTE_CATEGORY_ID] as String?,
            noteTitle = data[NoteTable.NOTE_TITLE] as String?,
            noteInfo = data[NoteTable.NOTE_INFO] as String?,
            deleteFlag = (data[NoteTable.DELETE_FLAG] as Long).toInt(),
            createdBy = data[NoteTable.CREATED_BY] as String?,
            createdAt = data[NoteTable.CREATED_AT] as String?,
            updatedAt = data[NoteTable.UPDATED_AT] as String?,
            syncFlag = 1,
        )
    }

}
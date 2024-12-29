package com.example.notesapp.data.workers.autosync

import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.notesapp.data.workers.note.NoteUploadWorker
import com.example.notesapp.data.workers.notecategory.NoteCategoryUploadWorker
import com.example.notesapp.data.workers.todos.TodosUploadWorker
import com.example.notesapp.data.workers.user.UserUploadWorker

/**
 * Sync up functionalities
 * */
object SyncUp {
    const val UNIQUE_WORK_SYNC_UP = "apps_sync_up"

    private const val UNIQUE_WORK_SYNC_UP_USERS = "sync_up_users"
    private const val UNIQUE_WORK_SYNC_UP_TODOS = "sync_up_todos"
    private const val UNIQUE_WORK_SYNC_UP_NOTES = "sync_up_notes"
    private const val UNIQUE_WORK_SYNC_UP_NOTE_CATEGORY = "sync_up_note_category"

    private val constraints = Constraints
        .Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresDeviceIdle(false)
        .build()

    private val uploadUsers = OneTimeWorkRequestBuilder<UserUploadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_UP_USERS)
        .addTag(UNIQUE_WORK_SYNC_UP)
        .build()

    private val uploadTodos = OneTimeWorkRequestBuilder<TodosUploadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_UP_TODOS)
        .addTag(UNIQUE_WORK_SYNC_UP)
        .build()

    private val uploadNotes = OneTimeWorkRequestBuilder<NoteUploadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_UP_NOTES)
        .addTag(UNIQUE_WORK_SYNC_UP)
        .build()

    private val uploadNoteCategory = OneTimeWorkRequestBuilder<NoteCategoryUploadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_UP_NOTE_CATEGORY)
        .addTag(UNIQUE_WORK_SYNC_UP)
        .build()

    fun syncUp(workManager: WorkManager){
        val moduleList = listOf(
            uploadUsers,
            uploadNotes,
            uploadNoteCategory,
            uploadTodos
        )
        workManager.beginUniqueWork(
            UNIQUE_WORK_SYNC_UP,
            ExistingWorkPolicy.REPLACE,
            moduleList
        ).enqueue()
    }
}
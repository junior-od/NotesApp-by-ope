package com.example.notesapp.data.workers.autosync

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.notesapp.data.notecategory.NoteCategory
import com.example.notesapp.data.workers.note.NoteDownloadWorker
import com.example.notesapp.data.workers.notecategory.NoteCategoryDownloadWorker
import com.example.notesapp.data.workers.todos.TodosDownloadWorker
import com.example.notesapp.data.workers.user.UserDownloadWorker
import com.example.notesapp.data.workers.user.UserUploadWorker

/**
 * Sync down functionalities
 * */
object SyncDown {
    const val UNIQUE_WORK_SYNC_DOWN = "apps_sync_down"

    private const val UNIQUE_WORK_SYNC_DOWN_USERS = "sync_down_users"
    private const val UNIQUE_WORK_SYNC_DOWN_TODOS = "sync_down_todos"
    private const val UNIQUE_WORK_SYNC_DOWN_NOTES = "sync_down_notes"
    private const val UNIQUE_WORK_SYNC_DOWN_NOTE_CATEGORY = "sync_down_note_category"

    private val constraints = Constraints
        .Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresDeviceIdle(false)
        .build()

    private val uploadUsers = OneTimeWorkRequestBuilder<UserDownloadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_DOWN_USERS)
        .addTag(UNIQUE_WORK_SYNC_DOWN)
        .build()

    private val uploadTodos = OneTimeWorkRequestBuilder<TodosDownloadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_DOWN_TODOS)
        .addTag(UNIQUE_WORK_SYNC_DOWN)
        .build()

    private val uploadNotes = OneTimeWorkRequestBuilder<NoteDownloadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_DOWN_NOTES)
        .addTag(UNIQUE_WORK_SYNC_DOWN)
        .build()

    private val uploadNoteCategory = OneTimeWorkRequestBuilder<NoteCategoryDownloadWorker>()
        .setConstraints(constraints)
        .addTag(UNIQUE_WORK_SYNC_DOWN_NOTE_CATEGORY)
        .addTag(UNIQUE_WORK_SYNC_DOWN)
        .build()

    fun syncDown(workManager: WorkManager){
        val moduleList = listOf(
            uploadUsers,
            uploadNotes,
            uploadNoteCategory,
            uploadTodos
        )
        workManager.beginUniqueWork(
            UNIQUE_WORK_SYNC_DOWN,
            ExistingWorkPolicy.REPLACE,
            moduleList
        ).enqueue()
    }
}
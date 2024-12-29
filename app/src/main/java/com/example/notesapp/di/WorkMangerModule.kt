package com.example.notesapp.di

import androidx.work.WorkManager
import com.example.notesapp.data.workers.autosync.AutoSyncWorker
import com.example.notesapp.data.workers.note.NoteDownloadWorker
import com.example.notesapp.data.workers.note.NoteUploadWorker
import com.example.notesapp.data.workers.notecategory.NoteCategoryDownloadWorker
import com.example.notesapp.data.workers.notecategory.NoteCategoryUploadWorker
import com.example.notesapp.data.workers.todos.TodosDownloadWorker
import com.example.notesapp.data.workers.todos.TodosUploadWorker
import com.example.notesapp.data.workers.user.UserUploadWorker
import com.example.notesapp.data.workers.user.UserDownloadWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

/**
 * this is the container to define how workmanager dependencies are to be provided across the app
 * */
val workMangerModule = module {
    // provide single instance of work manager
    single<WorkManager> {
        WorkManager.getInstance(androidApplication())
    }

    // provide worker instance of AutoSyncWorker
    workerOf(::AutoSyncWorker)

    // provide worker instance of UserUploadWorker
    workerOf(::UserUploadWorker)

    // provide worker instance of UserDownloadWorker
    workerOf(::UserDownloadWorker)

    // provide worker instance of TodosDownloadWorker
    workerOf(::TodosDownloadWorker)

    // provide worker instance of TodosUploadWorker
    workerOf(::TodosUploadWorker)

    // provide worker instance of NoteCategoryDownloadWorker
    workerOf(::NoteCategoryDownloadWorker)

    // provide worker instance of NoteCategoryUploadWorker
    workerOf(::NoteCategoryUploadWorker)

    // provide worker instance of NoteDownloadWorker
    workerOf(::NoteDownloadWorker)

    // provide worker instance of NoteUploadWorker
    workerOf(::NoteUploadWorker)

}
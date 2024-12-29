package com.example.notesapp.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.dsl.module

/**
 * this is the container to define how firebase dependencies are to be provided across the app
 * */

val firebaseModule = module {
    // provide a single instance of firebase auth
    single<FirebaseAuth> {
        Firebase.auth
    }

    // provide a single instance of firebase firestore
    single <FirebaseFirestore>{
        Firebase.firestore
    }
}
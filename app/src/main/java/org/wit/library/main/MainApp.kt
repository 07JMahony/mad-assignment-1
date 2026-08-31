package org.wit.library.main

import android.app.Application
import org.wit.library.models.BookJSONStore
import org.wit.library.models.BookStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var books: BookStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // Held behind the BookStore interface so the backing store can be swapped out.
        books = BookJSONStore(applicationContext)
        i("Library app started")
    }
}

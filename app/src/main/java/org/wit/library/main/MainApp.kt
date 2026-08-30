package org.wit.library.main

import android.app.Application
import org.wit.library.models.BookMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val books = BookMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Library app started")
    }
}

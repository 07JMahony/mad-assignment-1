package org.wit.shelfie.main

import android.app.Application
import org.wit.shelfie.models.BookMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val books = BookMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Shelfie started")
    }
}

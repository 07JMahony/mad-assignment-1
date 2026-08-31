package org.wit.library.helpers

import android.content.Context
import java.io.OutputStreamWriter

fun write(context: Context, fileName: String, data: String) {
    OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE)).use {
        it.write(data)
    }
}

fun read(context: Context, fileName: String): String {
    return context.openFileInput(fileName).bufferedReader().use { it.readText() }
}

fun exists(context: Context, fileName: String): Boolean {
    return context.getFileStreamPath(fileName).exists()
}

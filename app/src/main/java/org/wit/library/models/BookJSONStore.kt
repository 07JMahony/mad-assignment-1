package org.wit.library.models

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.wit.library.helpers.exists
import org.wit.library.helpers.read
import org.wit.library.helpers.write
import timber.log.Timber.i

const val JSON_FILE = "books.json"

private val gson = GsonBuilder().setPrettyPrinting().create()
private val listType = object : TypeToken<ArrayList<BookModel>>() {}.type

class BookJSONStore(private val context: Context) : BookStore {

    private var books = ArrayList<BookModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<BookModel> {
        return books
    }

    override fun create(book: BookModel) {
        // Continue from the highest id on file so ids survive a restart.
        book.id = (books.maxOfOrNull { it.id } ?: 0L) + 1
        books.add(book)
        serialize()
    }

    override fun update(book: BookModel) {
        val foundBook = books.find { it.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.author = book.author
            foundBook.genre = book.genre
            foundBook.rating = book.rating
            foundBook.read = book.read
            serialize()
        }
    }

    override fun delete(book: BookModel) {
        books.removeAll { it.id == book.id }
        serialize()
    }

    private fun serialize() {
        write(context, JSON_FILE, gson.toJson(books, listType))
        i("Saved ${books.size} books")
    }

    private fun deserialize() {
        // Gson returns null for an empty or blank file, so fall back to an empty list.
        books = gson.fromJson(read(context, JSON_FILE), listType) ?: ArrayList()
        i("Loaded ${books.size} books")
    }
}

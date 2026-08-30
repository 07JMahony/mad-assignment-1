package org.wit.library.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BookMemStore : BookStore {

    private val books = ArrayList<BookModel>()

    override fun findAll(): List<BookModel> {
        return books
    }

    override fun create(book: BookModel) {
        book.id = getId()
        books.add(book)
        logAll()
    }

    override fun update(book: BookModel) {
        val foundBook = books.find { it.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.author = book.author
            logAll()
        }
    }

    override fun delete(book: BookModel) {
        books.removeAll { it.id == book.id }
        logAll()
    }

    private fun logAll() {
        books.forEach { i("$it") }
    }
}

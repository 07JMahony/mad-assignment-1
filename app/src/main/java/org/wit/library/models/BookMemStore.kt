package org.wit.library.models

import timber.log.Timber.i

class BookMemStore : BookStore {

    private val books = ArrayList<BookModel>()

    override fun findAll(): List<BookModel> {
        return books
    }

    override fun create(book: BookModel) {
        book.id = (books.maxOfOrNull { it.id } ?: 0L) + 1
        books.add(book)
        logAll()
    }

    override fun update(book: BookModel) {
        val foundBook = books.find { it.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.author = book.author
            foundBook.genre = book.genre
            foundBook.rating = book.rating
            foundBook.read = book.read
            logAll()
        }
    }

    override fun delete(book: BookModel) {
        books.removeAll { it.id == book.id }
        logAll()
    }

    override fun deleteAll() {
        books.clear()
        logAll()
    }

    private fun logAll() {
        books.forEach { i("$it") }
    }
}

package org.wit.shelfie.models

interface BookStore {
    fun findAll(): List<BookModel>
    fun create(book: BookModel)
}

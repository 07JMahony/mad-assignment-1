package org.wit.library.models

interface BookStore {
    fun findAll(): List<BookModel>
    fun create(book: BookModel)
    fun update(book: BookModel)
    fun delete(book: BookModel)
    fun deleteAll()
}

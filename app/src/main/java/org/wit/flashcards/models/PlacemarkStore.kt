package org.wit.flashcards.models

interface PlacemarkStore {
    fun findAll(): List<FlashcardSetModel>
    fun create(placemark: FlashcardSetModel)
    fun update(placemark: FlashcardSetModel)
}
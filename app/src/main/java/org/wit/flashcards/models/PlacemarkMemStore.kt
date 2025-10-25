package org.wit.flashcards.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class PlacemarkMemStore : PlacemarkStore {

    val placemarks = ArrayList<FlashcardSetModel>()

    override fun findAll(): List<FlashcardSetModel> {
        return placemarks
    }

    override fun create(placemark: FlashcardSetModel) {
        placemark.id = getId()
        placemarks.add(placemark)
        logAll()
    }

    override fun update(placemark: FlashcardSetModel) {
        var foundPlacemark: FlashcardSetModel? = placemarks.find { p -> p.id == placemark.id }
        if (foundPlacemark != null) {
            foundPlacemark.title = placemark.title
            foundPlacemark.description = placemark.description
            logAll()
        }
    }

    private fun logAll() {
        placemarks.forEach { i("$it") }
    }
}
package org.wit.flashcards.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlashcardSetModel(var id: Long = 0,
                             var title: String = "",
                             var description: String = "") : Parcelable
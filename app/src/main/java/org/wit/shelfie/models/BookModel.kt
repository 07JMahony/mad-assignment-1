package org.wit.shelfie.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(var id: Long = 0,
                     var title: String = "",
                     var author: String = "") : Parcelable

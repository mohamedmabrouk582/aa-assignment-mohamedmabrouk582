package com.mabrouk.newstask.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mabrouk.newstask.core.convertDate
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
@Entity
@Parcelize
data class Article(
    @PrimaryKey
    val title: String,
    val author: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null,
    var country: String? = null,
    @Embedded
    val source: Source? = null
) : Parcelable {
    @Ignore
    fun format() = convertDate(publishedAt)
}

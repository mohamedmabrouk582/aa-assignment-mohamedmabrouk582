package com.mabrouk.newstask.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable

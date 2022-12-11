package com.mabrouk.newstask.domain.models

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Article>
)

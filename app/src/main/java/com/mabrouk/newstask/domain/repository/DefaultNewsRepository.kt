package com.mabrouk.newstask.domain.repository

import kotlinx.coroutines.flow.Flow
import com.mabrouk.newstask.core.Result
import com.mabrouk.newstask.domain.models.Article
import com.mabrouk.newstask.domain.models.ArticleResponse

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
interface DefaultNewsRepository {
    fun getAllArticles(country: String): Flow<Result<ArticleResponse>>
    suspend fun insertArticles(articles: ArrayList<Article>) : List<Long>
    suspend fun getSavedArticles(country: String): ArrayList<Article>
}
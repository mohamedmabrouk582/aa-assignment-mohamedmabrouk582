package com.mabrouk.newstask.domain.usecaes

import android.util.Log
import com.mabrouk.newstask.core.Result
import com.mabrouk.newstask.domain.models.Article
import com.mabrouk.newstask.domain.models.ArticleResponse
import com.mabrouk.newstask.domain.repository.DefaultNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
class ArticleUseCase @Inject constructor(val repository: DefaultNewsRepository) {
    fun getAllArticles(country: String): Flow<Result<ArticleResponse>> {
        return repository.getAllArticles(country)
    }

    suspend fun insertArticles(articles: ArrayList<Article>): List<Long> {
        return repository.insertArticles(articles)
    }

    suspend fun getSavedArticles(country: String): ArrayList<Article> {
        return repository.getSavedArticles(country)
    }
}
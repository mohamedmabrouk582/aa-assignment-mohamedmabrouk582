package com.mabrouk.newstask.presentaion.viewmodels

import com.mabrouk.newstask.core.Result
import com.mabrouk.newstask.core.TestUtils
import com.mabrouk.newstask.domain.models.Article
import com.mabrouk.newstask.domain.models.ArticleResponse
import com.mabrouk.newstask.domain.repository.DefaultNewsRepository
import kotlinx.coroutines.flow.Flow

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/11/22
 */
class FakeArticleRepository : DefaultNewsRepository {

    override fun getAllArticles(country: String): Flow<Result<ArticleResponse>> {
        return TestUtils.getArticles()
    }

    override suspend fun insertArticles(articles: ArrayList<Article>): List<Long> {
        TestUtils.savedArticles.addAll(articles)
        return listOf(articles.size.toLong())
    }

    override suspend fun getSavedArticles(country: String): ArrayList<Article> {
        return TestUtils.savedArticles
    }
}
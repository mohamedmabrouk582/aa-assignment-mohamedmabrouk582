package com.mabrouk.newstask.data.repository

import android.content.Context
import android.util.Log
import com.mabrouk.newstask.core.Result
import com.mabrouk.newstask.core.executeCall
import com.mabrouk.newstask.core.executeCall2
import com.mabrouk.newstask.core.toArrayList
import com.mabrouk.newstask.data.api.ArticleApi
import com.mabrouk.newstask.data.db.ArticleDao
import com.mabrouk.newstask.domain.models.Article
import com.mabrouk.newstask.domain.models.ArticleResponse
import com.mabrouk.newstask.domain.repository.DefaultNewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
class ArticleRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val api: ArticleApi,
    private val articleDao: ArticleDao
) : DefaultNewsRepository {

    override fun getAllArticles(country: String): Flow<Result<ArticleResponse>> {
        return executeCall2(context) {
            api.getAllArticlesAsync(country)
        }
    }

    override suspend fun insertArticles(articles: ArrayList<Article>): List<Long> {
        val insert = articleDao.insertArticles(articles)
        articleDao.getArticlesByCountry("EG").collectLatest {
            Log.d("ygygy",it.toString())
        }
        return insert
    }

    override suspend fun getSavedArticles(country: String): ArrayList<Article> {
        return articleDao.getArticlesByCountry(country).first().toArrayList()
    }

}
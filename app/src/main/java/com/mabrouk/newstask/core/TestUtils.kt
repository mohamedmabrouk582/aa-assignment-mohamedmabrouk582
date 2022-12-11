package com.mabrouk.newstask.core

import com.mabrouk.newstask.core.Result
import com.mabrouk.newstask.domain.models.Article
import com.mabrouk.newstask.domain.models.ArticleResponse
import kotlinx.coroutines.flow.flow

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/11/22
 */
object TestUtils {
    val savedArticles:ArrayList<Article> = arrayListOf()
    val articles = (1..5).map {
        Article(title = "Title#$it", publishedAt = "2022-12-11T00: 10: 31Z", country = "EG")
    }.toArrayList()

    fun getArticles() =
        flow {
            emit(Result.OnSuccess(ArticleResponse(status = "ok", 5, articles)))
        }

}
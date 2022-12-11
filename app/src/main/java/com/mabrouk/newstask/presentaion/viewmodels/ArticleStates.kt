package com.mabrouk.newstask.presentaion.viewmodels

import com.mabrouk.newstask.domain.models.Article

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
sealed class ArticleStates{
    object IDLE : ArticleStates()
    data class LoadArticles(val data:ArrayList<Article>) : ArticleStates()
    data class Error(val error:String) : ArticleStates()
}

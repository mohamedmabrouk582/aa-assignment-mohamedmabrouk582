package com.mabrouk.newstask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mabrouk.newstask.domain.models.Article
import kotlinx.coroutines.flow.Flow

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticles(articles: ArrayList<Article>): List<Long>

    @Query("select * from Article where country=:country")
    fun getArticlesByCountry(country: String): Flow<List<Article>>

}
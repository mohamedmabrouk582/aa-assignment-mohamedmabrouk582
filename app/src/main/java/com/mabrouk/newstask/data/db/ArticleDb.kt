package com.mabrouk.newstask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mabrouk.newstask.domain.models.Article

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
@Database(entities = [Article::class], version = 2, exportSchema = false)
abstract class ArticleDb : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}
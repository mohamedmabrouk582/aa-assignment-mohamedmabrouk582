package com.mabrouk.newstask.presentaion.di

import android.content.Context
import androidx.room.Room
import com.mabrouk.newstask.data.db.ArticleDao
import com.mabrouk.newstask.data.db.ArticleDb
import com.mabrouk.newstask.data.repository.ArticleRepository
import com.mabrouk.newstask.domain.usecaes.ArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @IoDispatcher
    @Provides
    fun getIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun getArticleDao(@ApplicationContext context: Context): ArticleDao =
        Room.databaseBuilder(context, ArticleDb::class.java, "news")
            .build().getArticleDao()

    @Provides
    @Singleton
    fun getArticleUsecase(repository: ArticleRepository) : ArticleUseCase =
        ArticleUseCase(repository)

}
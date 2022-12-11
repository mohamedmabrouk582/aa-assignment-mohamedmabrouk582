package com.mabrouk.newstask.di

import android.content.Context
import androidx.room.Room
import com.mabrouk.newstask.data.db.ArticleDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Named("test_db")
    fun getDB(@ApplicationContext context: Context): ArticleDb =
        Room.inMemoryDatabaseBuilder(context, ArticleDb::class.java).allowMainThreadQueries()
            .build()
}
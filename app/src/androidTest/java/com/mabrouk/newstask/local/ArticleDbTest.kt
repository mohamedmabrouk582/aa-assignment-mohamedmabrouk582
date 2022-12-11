package com.mabrouk.newstask.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mabrouk.newstask.core.CoroutineTestRule
import com.mabrouk.newstask.core.TestUtils
import com.mabrouk.newstask.data.db.ArticleDao
import com.mabrouk.newstask.data.db.ArticleDb
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/11/22
 */
@HiltAndroidTest
@SmallTest
@ExperimentalCoroutinesApi
class ArticleDbTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Inject
    @Named("test_db")
    lateinit var db : ArticleDb
    lateinit var dao : ArticleDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = db.getArticleDao()
    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun insertArticlesAndGetSavedArticles()= runBlocking {
        dao.insertArticles(TestUtils.articles)
       val articles =  dao.getArticlesByCountry("EG").first()
        assertThat(articles.size).isEqualTo(TestUtils.articles.size)
    }

}
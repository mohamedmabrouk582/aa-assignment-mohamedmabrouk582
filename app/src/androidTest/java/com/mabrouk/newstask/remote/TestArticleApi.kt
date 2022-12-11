package com.mabrouk.newstask.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.mabrouk.newstask.core.CoroutineTestRule
import com.mabrouk.newstask.data.api.ArticleApi
import com.mabrouk.newstask.domain.models.ArticleResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
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
class TestArticleApi {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    lateinit var mockWebServer: MockWebServer

    @Inject
    @Named("test_api")
    lateinit var articleApi: ArticleApi

    @Before
    fun setUp(){
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun getArticles()= runBlocking{
        val articles = articleApi.getAllArticlesAsync("EG").await()
        val items = MockServerDispatcher().getJsonContent("article.json")
        val data = Gson().fromJson(items, ArticleResponse::class.java)
        assertThat(articles).isEqualTo(data)
    }
}
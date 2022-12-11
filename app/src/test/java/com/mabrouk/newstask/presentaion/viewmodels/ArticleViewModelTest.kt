package com.mabrouk.newstask.presentaion.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mabrouk.newstask.core.CoroutineTestRule
import com.mabrouk.newstask.domain.usecaes.ArticleUseCase
import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import com.mabrouk.newstask.core.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking


import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/11/22
 */
class ArticleViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    lateinit var viewModel: ArticleViewModel


    @Before
    fun setUp() {
        viewModel = ArticleViewModel(ArticleUseCase(FakeArticleRepository()))
    }

    @Test
    fun `getAllArticles success`() {
        viewModel.getArticles("EG")
        val articles = viewModel.articlesStates.value as ArticleStates.LoadArticles
        assertThat(articles.data.size).isEqualTo(TestUtils.articles.size)
    }

    @Test
    fun `insertArticlesAndGetSavedArticles success`() = runBlocking{
        viewModel.getArticles("EG")
        val articles = viewModel.articlesStates.value as ArticleStates.LoadArticles
        viewModel.useCase.insertArticles(articles.data)
        assertThat(TestUtils.savedArticles.size>0).isTrue()
    }
}
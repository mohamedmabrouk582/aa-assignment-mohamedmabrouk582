package com.mabrouk.newstask.presentaion.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabrouk.newstask.core.Result
import com.mabrouk.newstask.core.countries
import com.mabrouk.newstask.core.toArrayList
import com.mabrouk.newstask.domain.usecaes.ArticleUseCase
import com.mabrouk.newstask.presentaion.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(
    val useCase: ArticleUseCase,
) : ViewModel() {
    private val _articlesStates = MutableStateFlow<ArticleStates>(ArticleStates.IDLE)
    val articlesStates: StateFlow<ArticleStates> = _articlesStates
    val loader: ObservableBoolean = ObservableBoolean()
    val countries = countries().keys.toList()
    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher


    fun getArticles(country: String) {
        viewModelScope.launch {
            useCase.getAllArticles(country).collectLatest {
                when (it) {
                    is Result.NoInternetConnect -> {
                        getSaved(country)
                        _articlesStates.value = ArticleStates.Error(it.error)
                    }
                    is Result.OnFailure ->
                        _articlesStates.value = ArticleStates.Error(it.msg)
                    Result.OnFinish -> loader.set(false)
                    Result.OnLoading -> loader.set(true)
                    is Result.OnSuccess -> {
                        _articlesStates.value = ArticleStates.LoadArticles(it.data.articles)
                        launch(ioDispatcher) {
                            useCase.insertArticles(it.data.articles.map { article ->
                                article.country = country
                                article
                            }.toArrayList())
                        }
                        if (it.data.totalResults == 0) {
                            _articlesStates.value = ArticleStates.Error("No Articles")
                            getSaved(country)
                        }
                    }
                }
            }
        }
    }

    private  fun getSaved(country: String){
        viewModelScope.launch {
            _articlesStates.value =
                ArticleStates.LoadArticles(useCase.getSavedArticles(country))
        }
    }

}
package com.mabrouk.newstask.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mabrouk.newstask.data.api.ArticleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 4/18/22
 */
@Module
@InstallIn(SingletonComponent::class)
class TestApiModule {

    @Provides
    @Named("test_api")
    fun getApi() : ArticleApi =
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ArticleApi::class.java)


}
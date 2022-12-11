package com.mabrouk.newstask.data.api

import com.mabrouk.newstask.BuildConfig
import com.mabrouk.newstask.domain.models.ArticleResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 12/10/22
 */
interface ArticleApi {
    @GET("top-headlines?apiKey=${BuildConfig.API_KEY}")
    fun getAllArticlesAsync(
        @Query("country") country: String
    ): Deferred<ArticleResponse>
}
package com.mabrouk.newstask.remote

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 4/19/22
 */
class MockServerDispatcher {
    fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }

    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/top-headlines?apiKey=91e82f0b9a834fc58c040b731536fe70&country=EG" -> MockResponse().setResponseCode(200).setBody(getJsonContent("article.json"))
                else -> MockResponse().setResponseCode(400)
            }
        }
    }


}
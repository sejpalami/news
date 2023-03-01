package com.example.news.api

import com.example.news.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v1/items")
    suspend fun getBreakingNews(
        @Query("lineupSlug")
        lineupSlug: String = "news",
        @Query("page")
        pageNumber: Int = 1
    ): Response<NewsResponse>
}
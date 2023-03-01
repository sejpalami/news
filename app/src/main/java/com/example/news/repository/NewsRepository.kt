package com.example.news.repository

import com.example.news.api.RetrofitInstance
import com.example.news.db.NewsResponseItemDatabase
import com.example.news.models.NewsResponseItem

class NewsRepository(
    val db: NewsResponseItemDatabase
) {
    suspend fun getBreakingNews(lineUpSlung: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(lineUpSlung, pageNumber)

    suspend fun upsert(article: NewsResponseItem) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: NewsResponseItem) = db.getArticleDao().deleteArticle(article)
}
package com.example.news.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.news.models.NewsResponseItem

@Dao
interface NewsResponseItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(newsResponseItem: NewsResponseItem): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<NewsResponseItem>>

    @Delete
    suspend fun deleteArticle(newsResponseItem: NewsResponseItem)
}
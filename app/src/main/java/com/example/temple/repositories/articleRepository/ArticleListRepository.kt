package com.example.temple.repositories.articleRepository

import android.content.Context
import com.example.temple.dataClasses.ArticleDataClass
import com.example.temple.jsonParser.ArticleJsonParse

class ArticleListRepository(private val context: Context) {

    fun getArticleLists(): List<ArticleDataClass> {
        return ArticleJsonParse.loadArticleJsonFromRaw(context)
    }

}
package com.example.temple.repositories.articleRepository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.example.temple.dataClasses.ArticleDataClass
import com.example.temple.jsonParser.ArticleJsonParse
import com.example.temple.mapper.toUiModel
import com.example.temple.modelDataClasses.ArticleUIDataClass

class ArticleRepository @Inject constructor (@param:ApplicationContext private val context: Context) {

    suspend fun getArticleLists(): List<ArticleUIDataClass> {
        return ArticleJsonParse.loadArticleJsonFromRaw(context).map { it.toUiModel() }
    }

}
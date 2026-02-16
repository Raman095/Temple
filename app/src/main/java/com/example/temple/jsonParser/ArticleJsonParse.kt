package com.example.temple.jsonParser

import android.content.Context
import com.example.temple.R
import com.example.temple.dataClasses.ArticleDataClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ArticleJsonParse {

    suspend fun loadArticleJsonFromRaw(context: Context): List<ArticleDataClass> =
        withContext(Dispatchers.IO){

        val inputStream = context.resources.openRawResource(R.raw.article)

        val reader = inputStream.bufferedReader().use { it.readText() }

        val listType = object : TypeToken<List<ArticleDataClass>>() {}.type

        Gson().fromJson(reader, listType)
    }

}
package com.example.temple.dataClasses

data class ArticleDataClass(
    val name: String,
    val image: String,
    val definition: String,
    val types: List<String>,
    val causes: List<String>,
    val symptoms: List<String>,
    val preventionStrategy: List<String>
)

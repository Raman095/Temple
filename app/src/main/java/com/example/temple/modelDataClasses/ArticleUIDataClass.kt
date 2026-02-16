package com.example.temple.modelDataClasses

// We are using this data class particularly for image, so that our app doesn't lag because of images.

data class ArticleUIDataClass(
    val name: String,
    val image: Int,
    val definition: String,
    val types: List<String>,
    val causes: List<String>,
    val symptoms: List<String>,
    val preventionStrategy: List<String>
)

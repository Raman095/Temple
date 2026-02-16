package com.example.temple.mapper

import com.example.temple.R
import com.example.temple.dataClasses.ArticleDataClass
import com.example.temple.modelDataClasses.ArticleUIDataClass

// we made mapper, because we don't want composable to compose again and again for each image

fun ArticleDataClass.toUiModel(): ArticleUIDataClass {
    return ArticleUIDataClass(
        name = name,
        image = image.toDrawableRes(),
        definition = definition,
        types = types,
        causes = causes,
        symptoms = symptoms,
        preventionStrategy = preventionStrategy
    )
}

private fun String.toDrawableRes(): Int {
    return when (this) {
        "cardiovascular" -> R.drawable.cardiovascular
        "diabetes" -> R.drawable.diabetes
        "epilepsy" -> R.drawable.epilepsy
        "asthma" -> R.drawable.asthma
        "cancer" -> R.drawable.cancer
        "alzheimer" -> R.drawable.alzheimer
        "tuberculosis" -> R.drawable.tuberculosis
        "hypertension" -> R.drawable.hypertension
        "kidney" -> R.drawable.kidney
        "liver" -> R.drawable.liver
        "migraine" -> R.drawable.migraine
        "anemia" -> R.drawable.anemia
        "osteoporosis" -> R.drawable.osteoporosis
        "depression" -> R.drawable.depression
        "pneumonia" -> R.drawable.pneumonia
        else -> R.drawable.health
    }
}
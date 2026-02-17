package com.example.temple.jsonParser

import android.content.Context
import com.example.temple.R
import com.example.temple.dataClasses.MedicineDataClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MedicineJsonParse {

    suspend fun loadMedicineJsonFromRaw(context: Context): List<MedicineDataClass> =
        withContext(Dispatchers.IO) {

            val inputStream = context.resources.openRawResource(R.raw.medicine)

            val reader = inputStream.bufferedReader().use { it.readText() }

            val listType = object : TypeToken<List<MedicineDataClass>>() {}.type

            Gson().fromJson(reader, listType)
        }
}
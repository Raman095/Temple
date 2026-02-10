package com.example.temple.jsonParser

import android.content.Context
import com.example.temple.R
import com.example.temple.dataClasses.EmergencyContacts
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object EmergencyJsonParse {

    fun loadJsonFromRaw(context: Context): List<EmergencyContacts> {

        val inputStream = context.resources.openRawResource(R.raw.emergency)

        val reader = inputStream.bufferedReader().use { it.readText() }

        val listType = object : TypeToken<List<EmergencyContacts>>() {}.type

        return Gson().fromJson(reader, listType)

    }

}
package com.example.temple.repositories

import android.content.Context
import com.example.temple.dataClasses.EmergencyContacts
import com.example.temple.jsonParser.EmergencyJsonParse

class EmergencyRepository(private val context: Context) {

    fun getData(): List<EmergencyContacts> {
        return EmergencyJsonParse.loadJsonFromRaw(context)
    }

}
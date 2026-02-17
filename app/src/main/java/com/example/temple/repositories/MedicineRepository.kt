package com.example.temple.repositories

import android.content.Context
import com.example.temple.dataClasses.MedicineDataClass
import com.example.temple.jsonParser.MedicineJsonParse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MedicineRepository @Inject constructor(@param: ApplicationContext private val context: Context) {

    suspend fun getMedicineData(): List<MedicineDataClass> {
        return MedicineJsonParse.loadMedicineJsonFromRaw(context)
    }

}
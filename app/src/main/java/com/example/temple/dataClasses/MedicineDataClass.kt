package com.example.temple.dataClasses

data class MedicineDataClass(
    val medicineName: String,
    val category: List<String>,
    val description: String,
    val uses: List<String>,
    val howToUse: String,
    val sideEffects: List<String>,
    val precautions: List<String>,
    val interactions: List<String>,
    val storageInstructions: String,
    val warnings: String
)

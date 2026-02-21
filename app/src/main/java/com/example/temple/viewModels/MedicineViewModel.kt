package com.example.temple.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.temple.dataClasses.MedicineDataClass
import com.example.temple.repositories.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(private val medicineRepository: MedicineRepository) :
    ViewModel() {

    // For Medicine List Screen
    private val _medicineList = MutableStateFlow<List<MedicineDataClass>>(emptyList())

    // For Medicine Detail Screen
    private val _selectedMedicine = MutableStateFlow<MedicineDataClass?>(null)
    val selectedMedicine: StateFlow<MedicineDataClass?> = _selectedMedicine

    init {
        fetchMedicineData()
    }

    fun fetchMedicineData() {
        viewModelScope.launch {
            _medicineList.value = medicineRepository.getMedicineData()
        }
    }

    fun selectedMedicine(name: String) {
        _selectedMedicine.value = _medicineList.value.find { it.medicineName == name }
    }

    // search text
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchChange(query: String) {
        _searchQuery.value = query
    }

    // selected chip
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    val allCategories: StateFlow<List<String>> =
        _medicineList.map { list ->
            buildSet {
                add("All")
                list.flatMap { it.category }.forEach { add(it) }
            }.toList()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),   // Keep calculation alive for 5 seconds after UI stops observing.
            listOf("All")
        )

    val filteredMedicines: StateFlow<List<MedicineDataClass>> =
        combine(_medicineList, _searchQuery, _selectedCategory) { list, query, category ->

            list.filter { medicine ->

                // Category filter
                val categoryMatch = category == "All" || medicine.category.contains(category)

                // Search filter
                val searchMatch =
                    if (query.isBlank()) true
                    else buildList {
                        add(medicine.medicineName)
                        add(medicine.description)
                        addAll(medicine.uses)
                        add(medicine.howToUse)
                        addAll(medicine.sideEffects)
                        addAll(medicine.precautions)
                        addAll(medicine.interactions)
                        add(medicine.storageInstructions)
                        add(medicine.warnings)
                    }.any {
                        it.contains(query, ignoreCase = true)
                    }
                categoryMatch && searchMatch
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}
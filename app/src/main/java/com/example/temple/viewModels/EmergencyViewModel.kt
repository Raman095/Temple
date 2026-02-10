package com.example.temple.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.temple.dataClasses.EmergencyContacts
import com.example.temple.repositories.EmergencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmergencyViewModel(private val emergencyRepository: EmergencyRepository): ViewModel() {

    private val _emergencyContact = MutableStateFlow<List<EmergencyContacts>> (emptyList())

    val emergencyContacts: StateFlow<List<EmergencyContacts>> = _emergencyContact

    init {
        loadEmergencyContacts()
    }

    fun loadEmergencyContacts() {
        viewModelScope.launch {
            _emergencyContact.value = emergencyRepository.getData()
        }
    }

}

class EmergencyViewModelFactory(private val emergencyRepository: EmergencyRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmergencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmergencyViewModel(emergencyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
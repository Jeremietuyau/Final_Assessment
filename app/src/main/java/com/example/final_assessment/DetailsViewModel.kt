package com.example.final_assessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val api: Api) : ViewModel() {
    private val _entities = MutableStateFlow<DashboardResponse?>(null)
    val entities: StateFlow<DashboardResponse?> = _entities

    fun getDashboardEntities(keypass: String) {
        viewModelScope.launch {
            val response = api.getDashboardEntities(keypass)
            _entities.value = response
        }
    }
}
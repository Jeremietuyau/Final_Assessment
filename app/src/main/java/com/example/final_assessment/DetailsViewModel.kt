package com.example.final_assessment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
//extends ViewModel
//used to store and manage UI-related data for the DetailsActivity
// primary constructor for the DetailsViewModel class. The @Inject annotation allows Dagger to provide an instance of the Api class.
class DetailsViewModel @Inject constructor(private val api: Api) : ViewModel() {
    //a MutableStateFlow that holds a nullable DashboardResponse object
    //an immutable version of _entities that exposes a StateFlow to observers, like the UI. By exposing only the StateFlow, it ensures that the value can be observed, but not modified outside the ViewModel.
    private val _entities = MutableStateFlow<DashboardResponse?>(null)
    val entities: StateFlow<DashboardResponse?> = _entities
    //responsible for fetching dashboard entities using the keypass
    fun getDashboardEntities(keypass: String) {
        //coroutine is launched within the ViewModel's lifecycle
        viewModelScope.launch {
            val response = api.getDashboardEntities(keypass)
            //Once the API call succeeds, the _entities state flow is updated with the new DashboardResponse from the server.
            _entities.value = response
        }
    }
}
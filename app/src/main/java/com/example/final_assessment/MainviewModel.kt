// MainViewModel.kt

package com.example.final_assessment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: Api) : ViewModel() {

    private val _loginSuccess = MutableLiveData<String?>()
    val loginSuccess: LiveData<String?> get() = _loginSuccess

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> get() = _loginError

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> get() = _toastMessage

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val loginResponse = withContext(Dispatchers.IO) {
                    api.login(LoginRequest(username, password))
                }
                // Send toast message event
                _toastMessage.postValue("Welcome $username")
                delay(2000)
                _loginSuccess.postValue(loginResponse.keypass)
            } catch (e: Exception) {
                _loginError.postValue("Login failed: ${e.message}")
            }
        }
    }
}


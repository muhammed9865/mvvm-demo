package com.salman.mvvmdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/2/2024.
 */
class LoginViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(LoginState())
    val state = mutableState.asStateFlow()


    fun onEmailChanged(newEmail: String) {
        println(newEmail)
        mutableState.update {
            it.copy(email = newEmail)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        mutableState.update {
            it.copy(password = newPassword)
        }
    }

    fun login() {
        viewModelScope.launch(context = Dispatchers.IO) {
            // Using a customized Coroutine scope that's bound to the LoginViewModel's lifecycle.
            mutableState.update {
                it.copy(isLoading = true)
            }
            // mimicking the behavior of login by delaying the current scope with 1500ms
            delay(10000)
            mutableState.update {
                it.copy(isLoading = false)
            }
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return LoginViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
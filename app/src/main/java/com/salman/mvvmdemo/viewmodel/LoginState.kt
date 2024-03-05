package com.salman.mvvmdemo.viewmodel

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/2/2024.
 */
data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isLoginEnabled: Boolean = false,
)

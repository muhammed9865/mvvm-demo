package com.salman.mvvmdemo.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.salman.mvvmdemo.R
import com.salman.mvvmdemo.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 3/2/2024.
 */
class LoginActivity : ComponentActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    // initializing viewModel using Factory approach
    private val viewModelWithDataState by lazy {
        ViewModelProvider(this, LoginViewModel.Factory)[LoginViewModel::class.java]
    }

    // initializing viewModel using activity extensions
    private val viewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        observeDataStateUpdates()
        // observeSealedStateUpdates()
        // Main, Default, I/O

    }

    private fun setListeners() {
        println("Listeners set")
        binding.emailEt.doOnTextChanged { text, _, _, _ ->
            viewModelWithDataState.onEmailChanged(text.toString())
        }
        binding.passwordEt.doOnTextChanged { text, _, _, _ ->
            viewModelWithDataState.onPasswordChanged(text.toString())
        }
        binding.loginBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModelWithDataState.login()
            }
        }
    }

    private fun observeDataStateUpdates() {
        lifecycleScope.launch {
            // Observing the state updates, this block will be called whenever
            // the viewModel mutates the state variable.
            viewModelWithDataState.state.collect { newState ->
                // This line is somehow considered a control action which is part of the business logic
                // It's not recommended to provide the conditions for [isEnabled] here.
                // What could be the solution?
                // FIXME the isEnabled condition should be collected from the newState variable
                binding.loginBtn.isEnabled = newState.email.isNotBlank() && newState.password.isNotBlank()
                binding.loadingPb.isVisible = newState.isLoading
                if (newState.isLoading) {
                    binding.loginBtn.text = ""
                } else {
                    binding.loginBtn.setText(R.string.login)
                }
            }
        }
    }
}
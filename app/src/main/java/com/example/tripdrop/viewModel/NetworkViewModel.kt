package com.example.tripdrop.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripdrop.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    // Add a loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val isConnected: StateFlow<Boolean> = connectivityObserver.isConnected
        .onEach {
            _isLoading.value = true  // Start loading when state changes
            delay(1000) // Simulate loader delay, you can adjust timing
            _isLoading.value = false // Stop loading after delay
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)
}

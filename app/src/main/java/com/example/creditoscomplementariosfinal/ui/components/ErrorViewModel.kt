package com.example.creditoscomplementariosfinal.ui.components

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class ErrorViewModel : ViewModel() {
    // Error dialog's visibility
    protected val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog.asStateFlow()

    protected val _dialogTitle = MutableStateFlow<String>("Error")
    val dialogTitle: StateFlow<String> = _dialogTitle.asStateFlow()

    // Error message display
    protected val _dialogErrorMessage = MutableStateFlow<String?>(null)
    val dialogErrorMessage: StateFlow<String?> = _dialogErrorMessage.asStateFlow()

    fun showErrorDialog(message: String, title: String = "Error") {
        _dialogTitle.value = title
        _dialogErrorMessage.value = message
        _showErrorDialog.value = true
    }

    fun dismissErrorDialog() {
        _showErrorDialog.value = false
        _dialogErrorMessage.value = null
    }
}
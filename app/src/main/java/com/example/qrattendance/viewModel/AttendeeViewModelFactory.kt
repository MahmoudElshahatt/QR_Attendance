package com.example.qrattendance.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.qrattendance.repository.AttendeeRepository

class AttendeeViewModelFactory(
    private val attendeeRepository: AttendeeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AttendeeViewModel(attendeeRepository) as T
    }
}
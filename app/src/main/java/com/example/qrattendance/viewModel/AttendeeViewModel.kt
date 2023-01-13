package com.example.qrattendance.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrattendance.model.Attendee
import com.example.qrattendance.repository.AttendeeRepository
import kotlinx.coroutines.launch

class AttendeeViewModel(private val attendeeRepository: AttendeeRepository) : ViewModel() {

    fun addAttendee(attendee: Attendee) = viewModelScope.launch {
        attendeeRepository.addAttendee(attendee)
    }

    fun getAllAttendees() = attendeeRepository.getAllAttendees()

    fun deleteAllAttendees() = viewModelScope.launch {
        attendeeRepository.deleteAllAttendees()
    }
}
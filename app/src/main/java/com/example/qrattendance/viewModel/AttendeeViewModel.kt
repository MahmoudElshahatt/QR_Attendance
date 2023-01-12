package com.example.qrattendance.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qrattendance.Attendee
import com.example.qrattendance.repository.AttendeeRepository
import kotlinx.coroutines.launch

class AttendeeViewModel(private val noteRepository: AttendeeRepository) : ViewModel() {

    fun addAttendee(attendee: Attendee) = viewModelScope.launch {
        noteRepository.addAttendee(attendee)
    }

    fun getAllAttendees() = noteRepository.getAllAttendees()

    //  fun searchNote(query: String) = noteRepository.searchNote(query)
}
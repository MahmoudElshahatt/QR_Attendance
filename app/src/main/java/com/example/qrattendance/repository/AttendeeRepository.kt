package com.example.qrattendance.repository

import com.example.qrattendance.Attendee
import com.example.qrattendance.database.AttendeeDataBase

class AttendeeRepository(private val db: AttendeeDataBase) {

    suspend fun addAttendee(attendee: Attendee) = db.getAttendeeDao().addAttendee(attendee)

    fun getAllAttendees() = db.getAttendeeDao().getAllAttendees()

    // fun searchNote(query: String) = db.getNoteDao().searchNote(query)
}
package com.example.qrattendance.repository

import com.example.qrattendance.model.Attendee
import com.example.qrattendance.database.AttendeeDataBase

class AttendeeRepository(private val db: AttendeeDataBase) {

    suspend fun addAttendee(attendee: Attendee) = db.getAttendeeDao().addAttendee(attendee)

    fun getAllAttendees() = db.getAttendeeDao().getAllAttendees()

    suspend fun deleteAllAttendees() = db.getAttendeeDao().deleteAllAttendees()
}
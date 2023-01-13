package com.example.qrattendance.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.qrattendance.model.Attendee

@Dao
interface AttendeeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAttendee(attendee: Attendee)

    @Query("SELECT * FROM attendees ORDER BY name")
    fun getAllAttendees(): LiveData<List<Attendee>>

    @Query("DELETE FROM attendees")
    suspend fun deleteAllAttendees()
}
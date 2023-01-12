package com.example.qrattendance.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.qrattendance.Attendee

@Dao
interface AttendeeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAttendee(attendee: Attendee)

    @Query("SELECT * FROM attendees ORDER BY name DESC")
    fun getAllAttendees(): LiveData<List<Attendee>>

//    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query")
//    fun searchNote(query: String?): LiveData<List<Note>>
}
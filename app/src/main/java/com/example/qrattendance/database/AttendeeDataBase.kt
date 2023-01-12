package com.example.qrattendance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qrattendance.Attendee

@Database(entities = [Attendee::class], version = 1)
abstract class AttendeeDataBase : RoomDatabase() {

    abstract fun getAttendeeDao(): AttendeeDAO

    companion object {

        @Volatile
        private var INSTANCE: AttendeeDataBase? = null

        fun getInstance(context: Context): AttendeeDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AttendeeDataBase::class.java,
                        "attendance_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
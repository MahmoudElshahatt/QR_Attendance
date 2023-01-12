package com.example.qrattendance.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "attendees")
@Parcelize
data class Attendee(
    @PrimaryKey
    val name: String="",
    val notes: String="",
    val time: String=""
) : Parcelable




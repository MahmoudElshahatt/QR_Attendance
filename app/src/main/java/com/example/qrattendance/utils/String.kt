package com.example.qrattendance

import com.example.qrattendance.model.Attendee

fun String.serialize(): Attendee {
    trim()
    val attendeeArguments = split("/").toTypedArray()
    var attendee: Attendee = Attendee()
    if (attendeeArguments.size == 3) {
        attendee = Attendee(
            name = attendeeArguments[0],
            notes = attendeeArguments[1],
            time = attendeeArguments[2]
        )
    }
    return attendee
}
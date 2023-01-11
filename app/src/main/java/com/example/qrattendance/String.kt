package com.example.qrattendance

fun String.serialize(): Attendee {
    trim()
    val arguments = split("/").toTypedArray()
    var attendee: Attendee = Attendee()
    if (arguments.size == 3) {
        attendee = Attendee(arguments[0], arguments[1], arguments[2])
    }
    return attendee
}
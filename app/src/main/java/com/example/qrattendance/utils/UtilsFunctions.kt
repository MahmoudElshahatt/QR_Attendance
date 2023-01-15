package com.example.qrattendance

import android.content.Context
import com.example.qrattendance.model.Attendee
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun String.serialize(): Attendee {
    trim()
    val attendeeArguments = split("/").toTypedArray()
    var attendee:Attendee = Attendee()
    if (attendeeArguments.isNotEmpty()) {
        attendee = Attendee(
            name = attendeeArguments[0],
            notes = attendeeArguments[1],
            time = getCurrentTime()
        )
    }
    return attendee
}

private fun getCurrentTime(): String {
    val dateFormatter = SimpleDateFormat("dd-MMM hh.mm aa", Locale.ENGLISH)
    val dateFormatted = (dateFormatter.format(Date())).toString()
    return dateFormatted
}

fun getCSVFileName(): String = "attendance.csv"

fun generateFile(context: Context, fileName: String): File? {
    val csvFile = File(context.filesDir, fileName)
    csvFile.createNewFile()
    return if (csvFile.exists()) {
        csvFile
    } else {
        null
    }
}

fun exportCSVFile(csvFile: File, attendees: List<Attendee>) {
    csvWriter().open(csvFile, append = false) {
        // Header
        writeRow(listOf("Name", "Notes", "Time"))
        attendees.forEach {
            writeRow(listOf(it.name, it.notes, it.time))
        }
    }
}
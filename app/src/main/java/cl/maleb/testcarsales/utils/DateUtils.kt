package cl.maleb.testcarsales.utils

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// Calendar variables
val c = Calendar.getInstance()
val year = c.get(Calendar.YEAR)
val month = c.get(Calendar.MONTH)
val day = c.get(Calendar.DAY_OF_MONTH)

val pattern = "yyyy-MM-dd"
val formatter = SimpleDateFormat(pattern)

fun initDate(): String {
    // method that helps to do the first API call. Returns the current date minus one day.
    val currentDay = day - 1
    val currentMonth = month
    val currentYear = year
    var dateString = ""


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now().minusDays(1)
        val formatter = DateTimeFormatter.ofPattern(pattern)
        dateString = current.format(formatter)
    } else {
        val date = Date(currentYear, currentMonth, currentDay)
        dateString = formatter.format(date)
    }

    return dateString
}

fun parseDateFromCalendar(dayOfMonth: Int, monthOfYear: Int, year: Int): String {
    var dateString = ""
    val calendar = Calendar.getInstance()
    calendar.set(year, monthOfYear, dayOfMonth)

    dateString = formatter.format(calendar.time)

    return dateString
}

fun Context.showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
    val dpd = DatePickerDialog(
        this,
        listener,
        year,
        month,
        day - 1
    )

    dpd.show()
}
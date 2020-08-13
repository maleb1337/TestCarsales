package cl.maleb.testcarsales.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
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

    val calendar = Calendar.getInstance()
    calendar.set(currentYear, currentMonth, currentDay)

    dateString = formatter.format(calendar.time)

    return dateString
}

fun isCurrentDate(intYear: Int, intMonth: Int, intDay: Int): Boolean {
    val dateToCompare = Calendar.getInstance()
    dateToCompare.set(intYear, intMonth, intDay)

    val currentDate = Calendar.getInstance()
    currentDate.set(year, month, day)

    if (formatter.format(dateToCompare.time) >= formatter.format(currentDate.time)) {
        return true
    }

    return false
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
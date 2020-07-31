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

fun initDate(): String {
    // method that helps to do the first API call. Returns the current date minus one day.
    val currentDay = day - 1
    val currentMonth = month
    val currentYear = year
    var dateString = ""


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now().minusDays(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateString = current.format(formatter)
    } else {
        val date = Date(currentYear, currentMonth, currentDay)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        dateString = formatter.format(date)
    }

    return dateString
}

fun getCurrentDay(): String {
    var dateString = ""

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        dateString = current.format(formatter)
    } else {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        dateString = formatter.format(date)
    }


    return dateString
}

fun parseDateFromCalendar(dayOfMonth: Int, monthOfYear: Int, year: Int): String {
    var date = ""
    if (dayOfMonth < 10) {
        if (monthOfYear < 10) {
            date = "$year-0$monthOfYear-0$dayOfMonth"
        } else {
            date = "$year-$monthOfYear-0$dayOfMonth"
        }

    } else {
        if (monthOfYear < 10) {
            date = "$year-0$monthOfYear-$dayOfMonth"
        } else {
            date = "$year-$monthOfYear-$dayOfMonth"
        }
    }

    return date
}

fun showDatePickerDialog(context: Context, listener: DatePickerDialog.OnDateSetListener) {
    val dpd = DatePickerDialog(
        context,
        listener,
        year,
        month,
        day
    )

    dpd.show()

}
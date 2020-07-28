package cl.maleb.testcarsales.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import java.util.*

// Calendar variables
val c = Calendar.getInstance()
val year = c.get(Calendar.YEAR)
val month = c.get(Calendar.MONTH)
val day = c.get(Calendar.DAY_OF_MONTH)

fun getCurrentDay(): String {
    var date = ""
    val currentDay = day - 1
    val currentMonth = month + 1
    val currentYear = year

    if (currentDay < 10) {
        if (currentMonth < 10) {
            date = "$currentYear-0$currentMonth-0$currentDay"
        } else {
            date = "$currentYear-$currentMonth-0$currentDay"
        }

    } else {
        if (currentMonth < 10) {
            date = "$currentYear-0$currentMonth-$currentDay"
        } else {
            date = "$currentYear-$currentMonth-$currentDay"
        }
    }

    return date
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

fun datePickerDialog(context: Context, listener: DatePickerDialog.OnDateSetListener) {
    var dateString = ""
    val dpd = DatePickerDialog(
        context,
        listener,
        year,
        month,
        day
    )

    dpd.show()

}
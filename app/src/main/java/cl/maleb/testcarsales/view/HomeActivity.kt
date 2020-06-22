package cl.maleb.testcarsales.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.maleb.testcarsales.R
import cl.maleb.testcarsales.adapter.HomeAdapter
import cl.maleb.testcarsales.viewModel.HomeActivityViewModel
import kotlinx.android.synthetic.main.activity_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)
        viewModel.showProgress.observe(this, Observer {
            if (it) {
                search_progress.visibility = VISIBLE
            } else {
                search_progress.visibility = GONE
            }
        })

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        viewModel.searchDate(getCurrentDay())

        viewModel.covidList.observe(this, Observer {
            adapter.setCovidList(listOf(it))
        })
        adapter = HomeAdapter(this)
        rv_home.adapter = adapter


        btnDateSelection.setOnClickListener {
            // dialog
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
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

                    viewModel.searchDate(date)
                },
                year,
                month,
                day
            )

            dpd.show()
        }


    }

    fun getCurrentDay(): String {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH) - 1

        var date = ""
        if (day < 10) {
            if (month < 10) {
                date = "$year-0$month-0$day"
            } else {
                date = "$year-$month-0$day"
            }

        } else {
            if (month < 10) {
                date = "$year-0$month-$day"
            } else {
                date = "$year-$month-$day"
            }
        }

        return date
    }
}
package cl.maleb.testcarsales.ui.covid

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.maleb.testcarsales.R
import cl.maleb.testcarsales.data.Result
import cl.maleb.testcarsales.databinding.CovidLayoutBinding
import cl.maleb.testcarsales.di.Injectable
import cl.maleb.testcarsales.di.injectViewModel
import cl.maleb.testcarsales.utils.showDatePickerDialog
import cl.maleb.testcarsales.utils.initDate
import cl.maleb.testcarsales.utils.parseDateFromCalendar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class CovidFragment : Fragment(R.layout.covid_layout), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CovidViewModel
    private lateinit var binding: CovidLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)
        binding = CovidLayoutBinding.bind(view)
        binding.btnDateSelection.setOnClickListener {
            // dialog
            showDatePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    val dateSelected = parseDateFromCalendar(day, month, year)
                    viewModel.fetchDate(dateSelected)
                }
            )
        }
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        viewModel.fetchDate(initDate())

        viewModelObserver()

    }

    private fun viewModelObserver() {
        viewModel.covidLiveData
            .observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Result.Status.SUCCESS -> {
                        viewModel.isShowProgress.value = false
                        viewModel.isShowContent.value = true
                        viewModel.selectedDate.value = it.data?.date
                        viewModel.confirmedCases.value = it.data?.confirmed
                        viewModel.deaths.value = it.data?.deaths
                    }
                    Result.Status.ERROR -> {
                        viewModel.isShowProgress.value = false
                        viewModel.isShowContent.value = false
                        showSnackBar()
                    }
                    Result.Status.LOADING -> {
                        viewModel.isShowProgress.value = true
                        viewModel.isShowContent.value = false
                    }
                }
            })
    }

    private fun showSnackBar() {
        val snack =
            Snackbar.make(
                requireView(),
                resources.getText(R.string.data_error),
                Snackbar.LENGTH_INDEFINITE
            )
        // I'd add an action to perform reload data
        snack.show()
    }


}
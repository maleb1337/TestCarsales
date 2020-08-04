package cl.maleb.testcarsales.ui.covid

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.maleb.testcarsales.R
import cl.maleb.testcarsales.data.Result
import cl.maleb.testcarsales.databinding.CovidLayoutBinding
import cl.maleb.testcarsales.di.Injectable
import cl.maleb.testcarsales.di.injectViewModel
import cl.maleb.testcarsales.utils.initDate
import cl.maleb.testcarsales.utils.parseDateFromCalendar
import cl.maleb.testcarsales.utils.showDatePickerDialog
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
            context?.showDatePickerDialog(
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
                        viewModel.successState(it.data)
                    }
                    Result.Status.ERROR -> {
                        viewModel.errorState()
                        showSnackBar()
                    }
                    Result.Status.LOADING -> {
                        viewModel.loadingState()
                    }
                    Result.Status.EMPTY -> {
                        viewModel.emptyState()
                        Toast.makeText(requireContext(), R.string.empty_data, Toast.LENGTH_LONG)
                            .show()
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
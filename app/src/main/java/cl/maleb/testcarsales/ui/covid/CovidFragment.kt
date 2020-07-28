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
import cl.maleb.testcarsales.utils.datePickerDialog
import cl.maleb.testcarsales.utils.getCurrentDay
import cl.maleb.testcarsales.utils.parseDateFromCalendar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class CovidFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CovidViewModel
    private lateinit var binding: CovidLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        // inflate layout
        binding = CovidLayoutBinding.inflate(inflater, container, false)
        binding.btnDateSelection.setOnClickListener {
            // dialog

            datePickerDialog(requireContext(),
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    viewModel.Date =
                        parseDateFromCalendar(day, month + 1, year)
                    /*
                        Here is my doubt, I'll still do another observer because, I couldn't find another way.
                        Iván sent me a guide project and that project also has two observers in the Fragment...
                        Link: https://github.com/amaljofy/NewsList-Part-1
                    */
                    viewModel.loadCovid().observe(viewLifecycleOwner, Observer {
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
            )
        }

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.Date = getCurrentDay()

        viewModel.loadCovid().observe(viewLifecycleOwner, Observer {
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
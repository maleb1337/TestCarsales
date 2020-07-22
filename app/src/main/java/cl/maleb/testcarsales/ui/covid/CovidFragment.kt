package cl.maleb.testcarsales.ui.covid

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cl.maleb.testcarsales.data.Result
import cl.maleb.testcarsales.databinding.CovidLayoutBinding
import cl.maleb.testcarsales.di.Injectable
import cl.maleb.testcarsales.di.injectViewModel
import java.util.*
import javax.inject.Inject

class CovidFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CovidViewModel
    private lateinit var binding: CovidLayoutBinding

    // Calendar variables
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

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
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    viewModel.Date =
                        viewModel.parseDateFromCalendar(dayOfMonth, monthOfYear + 1, year)

                    viewModel.loadCovid().observe(viewLifecycleOwner, Observer {
                        when (it.status) {
                            Result.Status.SUCCESS -> {
                                viewModel.isShowProgress.value = View.GONE
                                viewModel.isShowContent.value = View.VISIBLE
                                binding.covid = it.data
                            }
                            Result.Status.ERROR -> {
                                viewModel.isShowProgress.value = View.GONE
                            }
                            Result.Status.LOADING -> {
                                viewModel.isShowProgress.value = View.VISIBLE
                                viewModel.isShowContent.value = View.GONE
                            }
                        }
                    })

                },
                year,
                month,
                day
            )

            dpd.show()
        }

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.Date = viewModel.getCurrentDay()

        viewModel.covid.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    viewModel.isShowProgress.value = View.GONE
                    viewModel.isShowContent.value = View.VISIBLE
                    binding.covid = it.data
                }
                Result.Status.ERROR -> {
                    viewModel.isShowProgress.value = View.GONE
                }
                Result.Status.LOADING -> {
                    viewModel.isShowProgress.value = View.VISIBLE
                    viewModel.isShowContent.value = View.GONE
                }
            }
        })


    }


}
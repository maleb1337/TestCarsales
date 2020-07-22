package cl.maleb.testcarsales.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.maleb.testcarsales.ui.covid.CovidViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CovidViewModel::class)
    abstract fun bindNewsDetailsOneViewModel(viewModel: CovidViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

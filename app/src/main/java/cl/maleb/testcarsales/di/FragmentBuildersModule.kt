package cl.maleb.testcarsales.di

import cl.maleb.testcarsales.ui.covid.CovidFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCovidFragment(): CovidFragment

}

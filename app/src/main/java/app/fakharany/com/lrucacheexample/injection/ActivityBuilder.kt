package app.fakharany.com.lrucacheexample.injection

import app.fakharany.com.lrucacheexample.View.MainActivity
import app.fakharany.com.lrucacheexample.injection.Modules.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun provideMainActivity(): MainActivity
}
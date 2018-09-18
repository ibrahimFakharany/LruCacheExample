package app.fakharany.com.lrucacheexample.injection

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class,
ActivityBuilder::class])
interface AppComponent : AndroidInjector<MyApplication>{
}
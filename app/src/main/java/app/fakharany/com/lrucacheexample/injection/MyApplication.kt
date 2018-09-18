package app.fakharany.com.lrucacheexample.injection

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector {
    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)
        try {
            Realm.init(this)

            RealmConfiguration.Builder().name("cache_db")
                    .schemaVersion(1)
                    .build()
        } catch (e: Exception) {

        }
    }
}
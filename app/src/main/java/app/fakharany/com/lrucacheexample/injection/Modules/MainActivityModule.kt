package app.fakharany.com.lrucacheexample.injection.Modules

import app.fakharany.com.lrucacheexample.Model.MainActivityModel
import app.fakharany.com.lrucacheexample.Model.MainActivityModelImpl
import app.fakharany.com.lrucacheexample.Presenter.MainActivityPresenter
import app.fakharany.com.lrucacheexample.Presenter.MainActivityPresenterImpl
import app.fakharany.com.lrucacheexample.View.MainActivity
import app.fakharany.com.lrucacheexample.View.MainActivityView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {


    @Provides
    fun provideMainActivityPresenter(mainActivityView: MainActivityView, mainActivityModel: MainActivityModel): MainActivityPresenter {
        return MainActivityPresenterImpl(mainActivityView, mainActivityModel)
    }

    @Provides
    fun provideMainActivityView(mainActivity: MainActivity): MainActivityView {
        return mainActivity
    }


    @Provides
    fun provideMainActivityModel(): MainActivityModel {
        return MainActivityModelImpl()
    }

}
package app.fakharany.com.lrucacheexample.View

import app.fakharany.com.lrucacheexample.Presenter.MainActivityPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class TestMainActivityView {
    lateinit var mainActivityView: MainActivity
    var mainActivityPresenter = Mockito.mock(MainActivityPresenter::class.java)

    @Before
    fun setup() {
        mainActivityView = MainActivity()
    }

    @Test
    fun testGetBitmapFromMemCacheCalled() {

    }
}
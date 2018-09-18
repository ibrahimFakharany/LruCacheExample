package app.fakharany.com.lrucacheexample.Model

import app.fakharany.com.lrucacheexample.R
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class TestMainActivityModel {
    lateinit var mainActivityModel: MainActivityModel
    @Before
    fun setup() {
        mainActivityModel = MainActivityModelImpl()
    }

    @Test
    fun testGetFilenameCorrectly() {
        var data = "sample.json"
        var observer = TestObserver<String>()
        mainActivityModel.getFileName().subscribeWith(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValue(data)
    }

    @Test
    fun testGetImgResCorrectly() {
        var resId = R.drawable.scene
        var observer = TestObserver<Int>()
        mainActivityModel.getImgRes().subscribeWith(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValue(resId)
    }
}
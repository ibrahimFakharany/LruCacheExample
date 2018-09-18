package app.fakharany.com.lrucacheexample.Presenter

import app.fakharany.com.lrucacheexample.Model.MainActivityModel
import app.fakharany.com.lrucacheexample.View.MainActivityView
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestMainActivityPresenter {
    var mainActivityView = Mockito.mock(MainActivityView::class.java)
    var mainActivityModel = Mockito.mock(MainActivityModel::class.java)
    var mainActivityPresenter = MainActivityPresenterImpl(mainActivityView, mainActivityModel)

    @Test
    fun testLoadBitmapMethodCalledWhenCallOnMainActivityCreated() {
        var resId: Int = 15
        var filename = "sample.json"
        stubbingGetImgRes(resId)
        stubbingGetFilename(filename)
        mainActivityPresenter.onMainActivityCreated()
        verify(mainActivityView).loadBitmap(anyInt())
        verify(mainActivityView).loadJson(anyString())
    }


    @Test
    fun testShowJsonWhenOnLoadFinishedAndFilenameNotNull() {
        mainActivityPresenter.filename = anyString()
        mainActivityPresenter.onLoadJsonFinished(anyString())
        verify(mainActivityView).saveJsonToCache(anyString(), anyString())
        verify(mainActivityView).showJson(anyString())
    }

    @Test
    fun testShowJsonWhenOnLoadFinishedAndFilenameNull() {
        mainActivityPresenter.onLoadJsonFinished(anyString())
        verify(mainActivityView, never()).saveJsonToCache(anyString(), anyString())
        verify(mainActivityView).showJson(anyString())
    }

    @Test
    fun testSetIdlingResourceJsonTrueCalledWhenCallOnJsonSaved() {
        mainActivityPresenter.onJsonSaved()
        verify(mainActivityView).setIdlingResourceJsonTrue()
    }

    @Test
    fun testSetIdlingResourceBitmapTrueCalledWhenCallOnLoadBitmapFinished() {
        mainActivityPresenter.onLoadBitmapFinished()
        verify(mainActivityView).setIdlingResourceImageTrue()
    }


    @Test
    fun testCorrectJsonCalledWhenGetJsonFromResourcesCalled() {
        var data = "{\"username\": \"admin\",\"password\": \"password\"}"

        var classLoader = javaClass.classLoader
        var resourceUrl = classLoader.getResource("sample.json")
        var file = File(resourceUrl.path)

        var inputStream = file.inputStream()
        var returnedData = mainActivityPresenter.getJsonFromResources("sample.json",
                inputStream)
        assertEquals(data, returnedData)
    }

    @Test
    fun testRunJsonWorkerClassCalledWhenCallJsonNotFoundInCacheFunction() {
        mainActivityPresenter.jsonNotFoundInCache(anyString())
        verify(mainActivityView).runJsonWorkerClass(anyString())
    }


    @Test
    fun testBitmapFoundInCache() {
        mainActivityPresenter.bitmapFoundInCache(anyInt())
        verify(mainActivityView).onBitmapLoadedFromCache(anyInt())
    }



    fun stubbingGetImgRes(resId: Int) {
        Mockito.`when`(mainActivityModel.getImgRes()).thenReturn(Observable.just(resId))
    }

    fun stubbingGetFilename(filename: String) {
        Mockito.`when`(mainActivityModel.getFileName()).thenReturn(Observable.just(filename))
    }
}

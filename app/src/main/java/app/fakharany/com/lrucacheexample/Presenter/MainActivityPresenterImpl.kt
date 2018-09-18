package app.fakharany.com.lrucacheexample.Presenter

import app.fakharany.com.lrucacheexample.Model.MainActivityModel
import app.fakharany.com.lrucacheexample.View.MainActivityView
import io.reactivex.rxkotlin.subscribeBy
import java.io.InputStream
import kotlin.text.Charsets.UTF_8

open class MainActivityPresenterImpl(val mainActivityView: MainActivityView, val mainActivityModel: MainActivityModel) : MainActivityPresenter {
    var filename: String? = null
    override fun onAddJsonButtonClicked() {
        emittNewJsonFile()

    }

    override fun onJsonSaved() {
        mainActivityView.setIdlingResourceJsonTrue()
    }

    override fun onLoadBitmapFinished() {
        mainActivityView.setIdlingResourceImageTrue()
    }

    override fun onMainActivityCreated() {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        mainActivityView.onInitializationFinished(cacheSize)
        mainActivityModel.getImgRes().subscribe { resId ->
            mainActivityView.setIdlingResourceImageFalse()
            mainActivityView.loadBitmap(resId)
        }

        emittNewJsonFile()
    }


    fun emittNewJsonFile() {
        mainActivityModel.getFileName().subscribeBy { filename ->
            mainActivityView.setIdlingResourceJsonFalse()
            this.filename = filename
            mainActivityView.loadJson(filename)
        }

    }

    override fun onLoadJsonFinished(json: String?) {
        if (filename != null)
            mainActivityView.saveJsonToCache(filename, json)
        mainActivityView.showJson(json)
    }

    override fun bitmapNotFoundInCache(imgId: Int) {
        mainActivityView.showPlaceHolder()
        mainActivityView.runLoadBitmapWorkerTask(imgId)
    }

    override fun bitmapFoundInCache(resId: Int) {
        mainActivityView.onBitmapLoadedFromCache(resId)
        mainActivityView.setIdlingResourceImageTrue()
    }

    override fun getJsonFromResources(filename: String?, inputStream: InputStream): String {
        var json: String
        var thisInputStream: InputStream = inputStream
        var size = thisInputStream.available()
        var buffer = ByteArray(size)
        thisInputStream.read(buffer)
        thisInputStream.close()
        json = String(buffer, UTF_8)
        return json
    }

    override fun jsonFoundInCache(json: String) {
        mainActivityView.showJson(json)
        mainActivityView.setIdlingResourceJsonTrue()
    }

    override fun jsonNotFoundInCache(filename: String) {
        mainActivityView.runJsonWorkerClass(filename)
    }

}
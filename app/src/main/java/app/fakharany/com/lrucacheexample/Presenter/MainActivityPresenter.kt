package app.fakharany.com.lrucacheexample.Presenter

import java.io.InputStream

interface MainActivityPresenter {

    fun onMainActivityCreated()
    fun bitmapFoundInCache(resId: Int)
    fun bitmapNotFoundInCache(imgId: Int)
    fun onAddJsonButtonClicked()
    fun onLoadBitmapFinished()
    fun jsonFoundInCache(json: String)
    fun jsonNotFoundInCache(filename: String)
    fun getJsonFromResources(filename: String?, inputStream: InputStream): String
    fun onLoadJsonFinished(json: String?)
    fun onJsonSaved()

}
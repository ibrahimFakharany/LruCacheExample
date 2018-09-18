package app.fakharany.com.lrucacheexample.View

import android.graphics.Bitmap

interface MainActivityView {
    fun onInitializationFinished(cache: Int)
    fun setIdlingResourceImageFalse()
    fun setIdlingResourceImageTrue()
    fun setIdlingResourceJsonFalse()
    fun setIdlingResourceJsonTrue()
    fun loadBitmap(resId: Int)
    fun loadJson(filename: String)
    fun onBitmapLoadedFromCache(resId: Int)
    fun showPlaceHolder()
    fun runLoadBitmapWorkerTask(imgId: Int)
    fun runJsonWorkerClass(filename: String)
    fun showBitmap(bitmap: Bitmap)
    fun showJson(json: String?)
    fun saveJsonToCache(filename: String?, json: String?)

}
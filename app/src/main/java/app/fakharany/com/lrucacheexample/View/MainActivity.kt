package app.fakharany.com.lrucacheexample.View

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.util.LruCache
import android.support.v7.app.AppCompatActivity
import android.util.Log
import app.fakharany.com.lrucacheexample.Presenter.MainActivityPresenter
import app.fakharany.com.lrucacheexample.R
import app.fakharany.com.lrucacheexample.SimpleIdlingResource
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


open class MainActivity : AppCompatActivity(), MainActivityView {
    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter
    private lateinit var mMemoryBitmapCache: LruCache<String, Bitmap>
    private lateinit var mMemoryJsonCache: LruCache<String, String>
    var idlingResourceImage = SimpleIdlingResource()
    var idlingResourceJson = SimpleIdlingResource()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onCreateThisActivity()
        addJsonButton.setOnClickListener {
            mainActivityPresenter.onAddJsonButtonClicked()

        }
    }

    fun onCreateThisActivity() {
        mainActivityPresenter.onMainActivityCreated()
    }

    override fun setIdlingResourceJsonTrue() {
        idlingResourceJson.setIdleState(true)
    }

    override fun setIdlingResourceJsonFalse() {
        idlingResourceJson.setIdleState(false)
    }

    override fun setIdlingResourceImageTrue() {
        idlingResourceImage.setIdleState(true)
    }

    override fun setIdlingResourceImageFalse() {
        idlingResourceImage.setIdleState(false)
    }

    fun getMySimpleIdlingResourceImage(): SimpleIdlingResource {
        return idlingResourceImage
    }

    fun getMemoryBitmapCache(): LruCache<String, Bitmap> {
        return mMemoryBitmapCache
    }

    open fun saveBitmapToCache(resId: Int, it: Bitmap?) {
        mMemoryBitmapCache.put(resId.toString(), it)
    }

    override fun runLoadBitmapWorkerTask(imgId: Int) {
        BitmapWorkerTask().execute(imgId)
    }

    override fun showPlaceHolder() {
        var placeHolderId = R.drawable.place
        img.setImageResource(placeHolderId)
    }

    override fun loadBitmap(resId: Int) {
        val imageKey: String = resId.toString()
        getBitmapFromMemCache(imageKey)?.also {
            mainActivityPresenter.bitmapFoundInCache(resId)

        } ?: run {
            mainActivityPresenter.bitmapNotFoundInCache(resId)
            null
        }
    }

    private fun getBitmapFromMemCache(imageKey: String): Bitmap? {
        return mMemoryBitmapCache.get(imageKey)
    }

    override fun showBitmap(bitmap: Bitmap) {
        img.setImageBitmap(bitmap)
    }

    private inner class BitmapWorkerTask : AsyncTask<Int, Unit, Bitmap>() {

        // Decode image in background.
        override fun doInBackground(vararg params: Int?): Bitmap? {
            return params[0]?.let { resId ->
                BitmapFactory.decodeResource(resources, resId).also {

                    saveBitmapToCache(resId, it)
                }
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            mainActivityPresenter.onLoadBitmapFinished()
            img.setImageBitmap(result)
        }
    }

    fun getMySimpleIdlingResourceJson(): SimpleIdlingResource {
        return idlingResourceJson
    }

    fun getMemoryJsonCache(): LruCache<String, String> {
        return mMemoryJsonCache
    }

    override fun saveJsonToCache(key: String?, json: String?) {
        mMemoryJsonCache.put(key!!, json!!)
        mainActivityPresenter.onJsonSaved()
    }

    override fun runJsonWorkerClass(filename: String) {
        JSONWorkerTask().execute(filename)
    }

    override fun onBitmapLoadedFromCache(resId: Int) {
        img.setImageBitmap(BitmapFactory.decodeResource(resources, resId))
    }

    override fun onInitializationFinished(cacheSize: Int) {
        Log.e("activitymain", cacheSize.toString())
        mMemoryBitmapCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {

                return bitmap.byteCount / 1024
            }
        }
        // the number of entries in the cache
        mMemoryJsonCache = object : LruCache<String, String>(3) {
            override fun entryRemoved(evicted: Boolean, key: String?, oldValue: String?, newValue: String?) {

                // value is removed by remove method
                Log.e("mainactivity", "remove method , value is " + oldValue.toString() + " ${newValue}")
                // value is removed by put method

                Log.e("mainactivity", evicted.toString())
                super.entryRemoved(evicted, key, oldValue, newValue)

            }
        }
    }

    override fun showJson(json: String?) {
        var oldText = json_text.text?.toString()
        if (oldText != null)
            json_text.text = "${oldText} \n ${json}"
        else
            json_text.text = "${json}"

        scroll_view.scrollTo(0, json_text.bottom)
    }

    override fun loadJson(fileName: String) {
        getJsonFromCache(fileName)?.also { json ->
            mainActivityPresenter.jsonFoundInCache(json)
        } ?: run {
            mainActivityPresenter.jsonNotFoundInCache(fileName)
        }
    }

    fun getJsonFromCache(key: String): String? {
        return mMemoryJsonCache.get(key)
    }

    fun readJsonFile(filename: String?): String {
        return mainActivityPresenter.getJsonFromResources(filename, assets.open(filename))
    }

    private inner class JSONWorkerTask : AsyncTask<String, Unit, String>() {

        override fun doInBackground(vararg params: String?): String? {
            return params[0]?.let { filename ->
                readJsonFile(filename)
            }

        }

        override fun onPostExecute(json: String?) {
            super.onPostExecute(json)
            mainActivityPresenter.onLoadJsonFinished(json)
        }
    }

}
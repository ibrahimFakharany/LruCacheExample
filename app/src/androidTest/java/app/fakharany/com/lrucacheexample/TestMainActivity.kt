package app.fakharany.com.lrucacheexample

import android.graphics.Bitmap
import android.support.test.espresso.IdlingRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import app.fakharany.com.lrucacheexample.View.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestMainActivity {
    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)
    lateinit var simpleIdlingResourceImage: SimpleIdlingResource
    lateinit var simpleIdlingResourceJson: SimpleIdlingResource

    lateinit var mMemoryBitmapCache: android.support.v4.util.LruCache<String, Bitmap>
    lateinit var mMemoryJsonCache: android.support.v4.util.LruCache<String, String>
    @Before
    fun setup() {
        simpleIdlingResourceImage = activityRule.activity.getMySimpleIdlingResourceImage()
        simpleIdlingResourceJson = activityRule.activity.getMySimpleIdlingResourceJson()
        mMemoryBitmapCache = activityRule.activity.getMemoryBitmapCache()
        mMemoryJsonCache = activityRule.activity.getMemoryJsonCache()
        IdlingRegistry.getInstance().register(simpleIdlingResourceImage)
        IdlingRegistry.getInstance().register(simpleIdlingResourceJson)
    }

    @Test
    fun testBitmapCachedInMemory() {
        var resId = R.drawable.scene
        assertNotNull(mMemoryBitmapCache.get(resId.toString()))
    }

    @Test
    fun testJsonCached() {
        var data = "{\"username\": \"admin2\",\"password\": \"password2\"}"
        var filename = "sample2.json"
        assertNotNull(mMemoryJsonCache.get(filename))
        assertEquals(mMemoryJsonCache.get(filename), data)
    }
}
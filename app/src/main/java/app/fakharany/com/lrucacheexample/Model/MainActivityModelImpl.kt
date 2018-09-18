package app.fakharany.com.lrucacheexample.Model

import app.fakharany.com.lrucacheexample.R
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.ThreadLocalRandom

class MainActivityModelImpl : MainActivityModel {

    var position: Int = 0
    fun getNewFile(): String {
        position = ThreadLocalRandom.current().nextInt(0, getMyList().size)
        return getMyList()[position]
    }
    var list = arrayListOf("sample1.json",
            "sample2.json",
            "sample3.json",
            "sample4.json",
            "sample5.json",
            "sample6.json",
            "sample7.json",
            "sample8.json",
            "sample9.json",
            "sample10.json")
    fun getMyList(): ArrayList<String> {

        return list
    }

    override fun getFileName(): Single<String> {
        return Single.defer {
            Single.just(getNewFile())
        }
    }

    override fun getImgRes(): Observable<Int> {
        return Observable.defer {
            Observable.just(R.drawable.scene)
        }
    }
}
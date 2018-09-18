package app.fakharany.com.lrucacheexample.Model

import io.reactivex.Observable
import io.reactivex.Single

interface MainActivityModel {

    fun getImgRes(): Observable<Int>

    fun getFileName(): Single<String>
}
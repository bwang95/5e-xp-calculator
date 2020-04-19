package com.cerridan.androidtemplate.api

import com.cerridan.androidtemplate.api.response.ExampleResponse
import com.cerridan.androidtemplate.util.Result
import com.cerridan.androidtemplate.util.Result.Failure
import com.cerridan.androidtemplate.util.Result.Success
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io

class ExampleService(private val api: ExampleAPI) {
  private fun <T> Single<T>.emitResultOnMainThread(): Single<Result<T>> = this
    .subscribeOn(io())
    .map<Result<T>> { Success(it) }
    .onErrorReturn { Failure(it) }
    .observeOn(mainThread())

  fun getExampleResponse(): Single<Result<ExampleResponse>> = api
    .getExampleResponse()
    .emitResultOnMainThread()
}
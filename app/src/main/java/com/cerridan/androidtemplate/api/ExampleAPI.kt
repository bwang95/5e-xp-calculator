package com.cerridan.androidtemplate.api

import com.cerridan.androidtemplate.api.response.ExampleResponse
import io.reactivex.Single
import retrofit2.http.GET

interface ExampleAPI {
  companion object {
    const val BASE_URL = "https://example.com/"
  }

  @GET("endpoint")
  fun getExampleResponse(): Single<ExampleResponse>
}
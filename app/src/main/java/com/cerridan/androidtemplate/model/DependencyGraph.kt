package com.cerridan.androidtemplate.model

import android.content.Context
import com.cerridan.androidtemplate.BuildConfig
import com.cerridan.androidtemplate.api.ExampleAPI
import com.cerridan.androidtemplate.api.ExampleService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object DependencyGraph {
  private lateinit var context: Context

  private val okHttpClient by lazy {
    OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) BODY else NONE
      })
      .build()
  }

  private val moshi by lazy {
    Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  private val retrofitBuilder by lazy {
    Retrofit.Builder()
      .client(okHttpClient)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(MoshiConverterFactory.create(moshi))
  }

  private val exampleApi by lazy {
    retrofitBuilder.baseUrl(ExampleAPI.BASE_URL)
      .build()
      .create(ExampleAPI::class.java)
  }

  val exampleService by lazy { ExampleService(exampleApi) }

  fun initialize(context: Context) { this.context = context }
}
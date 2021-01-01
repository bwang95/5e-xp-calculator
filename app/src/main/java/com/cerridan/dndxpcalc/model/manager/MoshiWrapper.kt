package com.cerridan.dndxpcalc.model.manager

import com.cerridan.dndxpcalc.model.util.DateAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiWrapper {
  private val moshi = Moshi.Builder()
    .add(DateAdapterFactory())
    .add(KotlinJsonAdapterFactory())
    .build()

  fun <T> serialize(obj: T, clazz: Class<T>): String = moshi.adapter(clazz).toJson(obj)

  fun <T> deserialize(json: String, clazz: Class<T>): T? = moshi.adapter(clazz).fromJson(json)
}
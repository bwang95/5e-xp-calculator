package com.cerridan.dndxpcalc.model.manager

import com.cerridan.dndxpcalc.model.util.DateAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * Wraps a [Moshi] instance for serializing and deserializing JSON.
 *
 * @author Brian
 * @since December 31st, 2020
 */
object MoshiWrapper {
  private val moshi = Moshi.Builder()
    .add(DateAdapterFactory())
    .add(KotlinJsonAdapterFactory())
    .build()

  /** Serializes a given object to a JSON String. */
  fun <T> serialize(obj: T, type: Class<T>): String = moshi.adapter(type).toJson(obj)

  /**
   * Deserializes a given JSON String to an object.
   *
   * @return the deserialized object, if successful; null otherwise.
   */
  fun <T> deserialize(json: String, type: Class<T>): T? = try {
    moshi.adapter(type).fromJson(json)
  } catch (e: Exception) {
    null
  }
}
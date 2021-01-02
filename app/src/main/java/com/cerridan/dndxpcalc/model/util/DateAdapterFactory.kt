package com.cerridan.dndxpcalc.model.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.rawType
import java.lang.reflect.Type
import java.util.Date

/**
 * Date Adapter Factory for Moshi. Converts to and from [Long] timestamps.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class DateAdapterFactory : JsonAdapter.Factory {
  override fun create(
    type: Type,
    annotations: MutableSet<out Annotation>,
    moshi: Moshi
  ): JsonAdapter<*>? {
    return if (type.rawType == Date::class.java) {
      SimpleDateAdapter(moshi.adapter(Long::class.java))
    } else {
      null
    }
  }

  /**
   * Simple Date Adapter for Moshi. Converts to and from [Long] timestamps.
   */
  class SimpleDateAdapter(
    private val longAdapter: JsonAdapter<Long>
  ) : JsonAdapter<Date>() {
    override fun fromJson(reader: JsonReader): Date? =
      longAdapter.fromJson(reader)?.let(::Date)

    override fun toJson(writer: JsonWriter, value: Date?) =
      longAdapter.toJson(writer, value?.time)
  }
}
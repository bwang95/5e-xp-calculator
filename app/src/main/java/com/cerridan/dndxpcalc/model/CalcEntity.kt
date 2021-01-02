package com.cerridan.dndxpcalc.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

/**
 * Represents a single entity in an XP calculation.
 *
 * @author Brian
 * @since December 31st, 2020
 */
@JsonClass(generateAdapter = true)
data class CalcEntity(
  @Json(name = "id") val id: String = UUID.randomUUID().toString(),
  @Json(name = "type") val type: EntityType,
  @Json(name = "value_type") val valueType: ValueType,
  @Json(name = "quantity") val quantity: Int,
  @Json(name = "value") val value: Int
)

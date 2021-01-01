package com.cerridan.dndxpcalc.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class CalcEntityList(
  @Json(name = "date") val date: Date,
  @Json(name = "entities") val entities: List<CalcEntity>
)
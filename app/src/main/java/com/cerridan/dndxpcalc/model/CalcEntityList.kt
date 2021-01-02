package com.cerridan.dndxpcalc.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

/**
 * A list of [CalcEntity] objects, used for writing to disk.
 *
 * @see com.cerridan.dndxpcalc.model.manager.SavedStateManager
 *
 * @author Brian
 * @since December 31st, 2020
 */
@JsonClass(generateAdapter = true)
data class CalcEntityList(
  @Json(name = "date") val date: Date,
  @Json(name = "entities") val entities: List<CalcEntity>
)
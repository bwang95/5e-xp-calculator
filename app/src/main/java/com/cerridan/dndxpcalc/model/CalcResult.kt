package com.cerridan.dndxpcalc.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Represents a calculation result.
 *
 * @see com.cerridan.dndxpcalc.model.util.XPCalculator
 *
 * @author Brian
 * @since December 31st, 2020
 */
@JsonClass(generateAdapter = true)
data class CalcResult(
  @Json(name = "monster_xp") val monsterXp: Int,
  @Json(name = "encounter_thresholds") val encounterThresholds: List<Int>,
  @Json(name = "threshold_index") val thresholdIdx: Int,
  @Json(name = "adjusted_xp_mult") val adjustedXpMultiplier: Double,
  @Json(name = "adjusted_xp") val adjustedXp: Int,
  @Json(name = "divided_monster_xp") val dividedMonsterXp: Int,
  @Json(name = "divided_adjusted_xp") val dividedAdjustedXp: Int
)
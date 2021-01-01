package com.cerridan.dndxpcalc.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ruleset(
  @Json(name = "cr_to_xp") val crToXp: List<CRMapping>,
  @Json(name = "player_levels") val playerLevels: List<PlayerLevel>,
  @Json(name = "encounter_thresholds") val encounterThresholds: List<EncounterThreshold>,
  @Json(name = "encounter_multipliers") val encounterMultipliers: List<EncounterMultiplier>,
) {
  @JsonClass(generateAdapter = true)
  data class PlayerLevel(
    @Json(name = "level") val level: Int,
    @Json(name = "xp") val xp: Int
  )

  @JsonClass(generateAdapter = true)
  data class CRMapping(
    @Json(name = "cr") val cr: Int,
    @Json(name = "xp") val xp: Int
  )

  @JsonClass(generateAdapter = true)
  data class EncounterThreshold(
    @Json(name = "level") val level: Int,
    @Json(name = "thresholds") val thresholds: List<Int>
  )

  @JsonClass(generateAdapter = true)
  data class EncounterMultiplier(
    @Json(name = "min_monsters") val minMonsters: Int,
    @Json(name = "multiplier") val multipler: Double
  )
}
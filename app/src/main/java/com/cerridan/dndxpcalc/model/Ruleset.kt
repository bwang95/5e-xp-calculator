package com.cerridan.dndxpcalc.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A packaged Ruleset for use by the XP Calculator.
 *
 * @param crToXp Monster CR to XP given.
 * @param playerLevels Player levels by XP.
 * @param encounterThresholds Thresholds for encounter difficulty by player level.
 * @param encounterMultipliers Number of monsters to adjusted XP multiplier.
 *
 * @author Brian
 * @since December 31st, 2020
 */
@JsonClass(generateAdapter = true)
data class Ruleset(
  @Json(name = "cr_to_xp") val crToXp: List<CRMapping>,
  @Json(name = "player_levels") val playerLevels: List<PlayerLevel>,
  @Json(name = "encounter_thresholds") val encounterThresholds: List<EncounterThreshold>,
  @Json(name = "encounter_multipliers") val encounterMultipliers: List<EncounterMultiplier>,
) {

  /** Mapping of a single player level to XP required. */
  @JsonClass(generateAdapter = true)
  data class PlayerLevel(
    @Json(name = "level") val level: Int,
    @Json(name = "xp") val xp: Int
  )

  /** Mapping of CR to XP granted. */
  @JsonClass(generateAdapter = true)
  data class CRMapping(
    @Json(name = "cr") val cr: Int,
    @Json(name = "xp") val xp: Int
  )

  /** Mapping for XP difficulty thresholds by level. */
  @JsonClass(generateAdapter = true)
  data class EncounterThreshold(
    @Json(name = "level") val level: Int,
    @Json(name = "thresholds") val thresholds: List<Int>
  )

  /** Encounter adjusted XP multiplier by monsters. */
  @JsonClass(generateAdapter = true)
  data class EncounterMultiplier(
    @Json(name = "min_monsters") val minMonsters: Int,
    @Json(name = "multiplier") val multipler: Double
  )
}
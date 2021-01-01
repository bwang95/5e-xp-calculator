package com.cerridan.dndxpcalc.model.util

import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.exception.CalculationException
import com.cerridan.dndxpcalc.model.CalcEntity
import com.cerridan.dndxpcalc.model.CalcResult
import com.cerridan.dndxpcalc.model.EntityType.CHARACTER
import com.cerridan.dndxpcalc.model.EntityType.MONSTER
import com.cerridan.dndxpcalc.model.Ruleset
import com.cerridan.dndxpcalc.model.ValueType.CR
import com.cerridan.dndxpcalc.model.ValueType.LEVEL
import com.cerridan.dndxpcalc.model.ValueType.XP
import com.cerridan.dndxpcalc.model.manager.RulesetManager
import com.cerridan.dndxpcalc.model.manager.SavedStateManager
import kotlin.math.roundToInt

object XPCalculator {
  private class RulesetModel(
    val crToXp: Map<Int, Int>,
    val xpToPlayerLevel: List<Pair<Int, Int>>,
    val levelsToDifficulty: Map<Int, List<Int>>,
    val monstersToMultipliers: List<Pair<Int, Double>>
  )

  private val ruleset by lazy { createRulesetModel(RulesetManager.ruleset) }

  fun calculate(entities: List<CalcEntity>): CalcResult {
    val players = entities.filter { it.type == CHARACTER }
    val playerQuantity = players.fold(0) { result, player -> result + player.quantity }
    val (monsterXp, multiplier) = calculateMonsterValues(entities.filter { it.type == MONSTER })
    val thresholds = generatePlayerThresholds(players)
    val adjustedXp = (monsterXp * multiplier).roundToInt()

    return CalcResult(
      monsterXp = monsterXp,
      encounterThresholds = thresholds,
      thresholdIdx = thresholds.indexOfLast { it <= adjustedXp },
      adjustedXpMultiplier = multiplier,
      adjustedXp = adjustedXp,
      dividedMonsterXp = monsterXp / playerQuantity,
      dividedAdjustedXp = adjustedXp / playerQuantity
    )
  }

  private fun generatePlayerThresholds(players: List<CalcEntity>): List<Int> {
    val playerThresholds = players.map { player ->
      val level = when (player.valueType) {
        LEVEL -> player.value
        XP -> ruleset.xpToPlayerLevel.last { (xp, _) -> xp <= player.value }.second
        CR -> 0
      }
      ruleset.levelsToDifficulty
        .getOrElse(level) { throw CalculationException(R.string.error_invalid_level, "Invalid Level") }
        .map { it * player.quantity }
    }
    val difficultyThresholds = MutableList(playerThresholds.first().size) { 0 }
    playerThresholds.forEach { thresholds ->
      thresholds.forEachIndexed { idx, threshold -> difficultyThresholds[idx] += threshold }
    }
    return difficultyThresholds
  }

  private fun calculateMonsterValues(monsters: List<CalcEntity>): Pair<Int, Double> {
    val monsterXp = monsters.fold(0) { result, entity ->
      val xp = when (entity.valueType) {
        LEVEL -> 0
        XP -> entity.value
        CR -> ruleset.crToXp.getOrElse(entity.value) {
          throw CalculationException(R.string.error_invalid_cr, "Invalid CR Value")
        }
      }
      result + (xp * entity.quantity)
    }
    val quantity = monsters.fold(0) { result, monster -> result + monster.quantity }
    val multiplier = ruleset.monstersToMultipliers.last { (min, _) -> min <= quantity }.second

    return monsterXp to multiplier
  }

  private fun createRulesetModel(ruleset: Ruleset): RulesetModel {
    val crToXp = mutableMapOf<Int, Int>()
    ruleset.crToXp.forEach { crToXp[it.cr] = it.xp }
    val levelsToThresholds = mutableMapOf<Int, List<Int>>()
    ruleset.encounterThresholds.forEach { levelsToThresholds[it.level] = it.thresholds }

    return RulesetModel(
      crToXp = crToXp,
      levelsToDifficulty = levelsToThresholds,
      xpToPlayerLevel = ruleset.playerLevels
        .sortedBy(Ruleset.PlayerLevel::xp)
        .map { it.xp to it.level },
      monstersToMultipliers = ruleset.encounterMultipliers
        .sortedBy(Ruleset.EncounterMultiplier::minMonsters)
        .map { it.minMonsters to it.multipler }
    )
  }
}
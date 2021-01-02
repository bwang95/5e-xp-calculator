package com.cerridan.dndxpcalc.model

import androidx.annotation.StringRes
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.model.ValueType.CR
import com.cerridan.dndxpcalc.model.ValueType.LEVEL
import com.cerridan.dndxpcalc.model.ValueType.XP

/**
 * Denotes the type of entity, Character or Monster.
 *
 * @param header The string resource for the header to display.
 * @param validValueTypes valid [ValueType]s for this [EntityType].
 *
 * @author Brian
 * @since December 31st, 2020
 */
enum class EntityType(
  @StringRes val header: Int,
  val validValueTypes: List<ValueType>
) {

  /** A player character. */
  CHARACTER(
    R.string.header_character,
    listOf(LEVEL, XP)
  ),

  /** A monster/enemy. */
  MONSTER(
    R.string.header_monster,
    listOf(XP, CR)
  )
}
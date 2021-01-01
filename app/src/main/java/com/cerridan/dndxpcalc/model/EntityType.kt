package com.cerridan.dndxpcalc.model

import androidx.annotation.StringRes
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.model.ValueType.CR
import com.cerridan.dndxpcalc.model.ValueType.LEVEL
import com.cerridan.dndxpcalc.model.ValueType.XP

enum class EntityType(
  @StringRes val header: Int,
  val validValueTypes: List<ValueType>
) {
  CHARACTER(
    R.string.header_character,
    listOf(LEVEL, XP)
  ),
  MONSTER(
    R.string.header_monster,
    listOf(XP, CR)
  )
}
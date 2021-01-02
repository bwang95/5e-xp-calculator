package com.cerridan.dndxpcalc.model

import androidx.annotation.StringRes
import com.cerridan.dndxpcalc.R

/**
 * Value type for [CalcEntity].
 *
 * @param description The user-facing description for the value type.
 *
 * @author Brian
 * @since December 31st, 2020
 */
enum class ValueType(
  @StringRes val description: Int
) {
  /** Player level. */
  LEVEL(R.string.description_level),

  /** Player or monster XP. */
  XP(R.string.description_xp),

  /** Monster CR. */
  CR(R.string.description_cr)
}
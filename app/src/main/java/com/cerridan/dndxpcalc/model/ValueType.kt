package com.cerridan.dndxpcalc.model

import androidx.annotation.StringRes
import com.cerridan.dndxpcalc.R

enum class ValueType(@StringRes val description: Int) {
  LEVEL(R.string.description_level),
  XP(R.string.description_xp),
  CR(R.string.description_cr)
}
package com.cerridan.dndxpcalc.adapter.calc

import androidx.annotation.StringRes
import com.cerridan.dndxpcalc.model.CalcEntity

sealed class CalculatorEpoxyModel(val id: String) {
  class HeaderItem(@StringRes val header: Int) : CalculatorEpoxyModel(header.toString())

  class EntityItem(val entity: CalcEntity) : CalculatorEpoxyModel(entity.id)
}
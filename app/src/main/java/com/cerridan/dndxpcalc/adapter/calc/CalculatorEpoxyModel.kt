package com.cerridan.dndxpcalc.adapter.calc

import androidx.annotation.StringRes
import com.cerridan.dndxpcalc.model.CalcEntity

/**
 * Epoxy models for the [CalculatorEpoxyController].
 *
 * @author Brian
 * @since December 31st, 2020
 */
sealed class CalculatorEpoxyModel(val id: String) {

  /**
   * Header item.
   *
   * @see com.cerridan.dndxpcalc.ui.HeaderItemView
   * @param header header text resource to display.
   */
  class HeaderItem(@StringRes val header: Int) : CalculatorEpoxyModel(header.toString())

  /**
   * Entity item.
   *
   * @see com.cerridan.dndxpcalc.ui.EntityItemView
   * @param entity Entity to display.
   */
  class EntityItem(val entity: CalcEntity) : CalculatorEpoxyModel(entity.id)
}
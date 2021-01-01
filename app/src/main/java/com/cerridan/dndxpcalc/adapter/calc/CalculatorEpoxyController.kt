package com.cerridan.dndxpcalc.adapter.calc

import com.cerridan.dndxpcalc.adapter.BaseEpoxyController
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel.EntityItem
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel.HeaderItem
import com.cerridan.dndxpcalc.ui.entityItemView
import com.cerridan.dndxpcalc.ui.headerItemView

class CalculatorEpoxyController(
  private val callbacks: CalculatorCallbacks
) : BaseEpoxyController<CalculatorEpoxyModel>() {
  override fun addViewForModel(position: Int, model: CalculatorEpoxyModel) = when (model) {
    is HeaderItem -> headerItemView {
      id(model.id)
      header(model.header)
    }
    is EntityItem -> entityItemView {
      id(model.id)
      entity(model.entity)
      callbacks(callbacks)
    }
  }
}
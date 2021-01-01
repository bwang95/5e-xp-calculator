package com.cerridan.dndxpcalc.adapter.calc

import com.cerridan.dndxpcalc.model.CalcEntity

interface CalculatorCallbacks {
  fun onEntityItemClicked(entity: CalcEntity)
}
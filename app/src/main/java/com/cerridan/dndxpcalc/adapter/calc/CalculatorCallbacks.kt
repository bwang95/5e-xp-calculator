package com.cerridan.dndxpcalc.adapter.calc

import com.cerridan.dndxpcalc.model.CalcEntity

/**
 * Callbacks used by the CalculatorFragment.
 *
 * @see CalculatorEpoxyController
 * @see com.cerridan.dndxpcalc.fragment.CalculatorFragment
 * @see com.cerridan.dndxpcalc.viewmodel.CalculatorViewModel
 *
 * @author Brian
 * @since December 31st, 2020
 */
interface CalculatorCallbacks {

  /** Called when an item describing a [CalcEntity] is clicked. */
  fun onEntityItemClicked(entity: CalcEntity)
}

package com.cerridan.dndxpcalc.adapter.result

import com.cerridan.dndxpcalc.adapter.BaseEpoxyController
import com.cerridan.dndxpcalc.ui.resultItemView

/**
 * Epoxy controller for the ResultFragment.
 *
 * @see com.cerridan.dndxpcalc.fragment.ResultFragment
 *
 * @author Brian
 * @since December 31st, 2020
 */
class ResultEpoxyController : BaseEpoxyController<ResultEpoxyModel>() {

  override fun addViewForModel(position: Int, model: ResultEpoxyModel) {
    resultItemView {
      id(model.title.toString())
      title(model.title)
      value(model.value)
    }
  }
}
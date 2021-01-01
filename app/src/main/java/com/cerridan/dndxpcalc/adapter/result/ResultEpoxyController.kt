package com.cerridan.dndxpcalc.adapter.result

import com.cerridan.dndxpcalc.adapter.BaseEpoxyController
import com.cerridan.dndxpcalc.ui.resultItemView

class ResultEpoxyController : BaseEpoxyController<ResultEpoxyModel>() {
  override fun addViewForModel(position: Int, model: ResultEpoxyModel) {
    resultItemView {
      id(model.title.toString())
      title(model.title)
      value(model.value)
    }
  }
}
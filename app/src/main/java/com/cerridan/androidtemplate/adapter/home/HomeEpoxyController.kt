package com.cerridan.androidtemplate.adapter.home

import com.cerridan.androidtemplate.adapter.BaseEpoxyController
import com.cerridan.androidtemplate.adapter.home.HomeEpoxyModel.ListItem
import com.cerridan.androidtemplate.ui.listitem.home.homeListItemView

class HomeEpoxyController(
  private val callbacks: HomeCallbacks
) : BaseEpoxyController<HomeEpoxyModel>() {
  override fun addViewForModel(position: Int, model: HomeEpoxyModel) = when (model) {
    is ListItem -> homeListItemView {
      id(model.id)
      title(model.title)
      data(model.data)
      homeCallbacks(callbacks)
    }
  }
}
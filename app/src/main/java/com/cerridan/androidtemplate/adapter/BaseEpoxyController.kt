package com.cerridan.androidtemplate.adapter

import com.airbnb.epoxy.TypedEpoxyController

abstract class BaseEpoxyController<T> : TypedEpoxyController<List<T>>() {
  override fun buildModels(data: List<T>) {
    data.forEachIndexed { index, model -> addViewForModel(index, model) }
  }

  abstract fun addViewForModel(position: Int, model: T)
}
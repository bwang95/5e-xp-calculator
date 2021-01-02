package com.cerridan.dndxpcalc.adapter

import androidx.annotation.CallSuper
import com.airbnb.epoxy.TypedEpoxyController

/**
 * Provides a simple wrapper on Epoxy functionality by taking model items and
 * requesting a view for each one.
 *
 * @author Brian
 * @since December 31st, 2020
 */
abstract class BaseEpoxyController<T> : TypedEpoxyController<List<T>>() {

  @CallSuper override fun buildModels(data: List<T>) {
    data.forEachIndexed { index, model -> addViewForModel(index, model) }
  }

  /**
   * Called when models are built. Subclasses should add a model view here.
   *
   * @param position the position of the model in the list.
   * @param model the model for which a view should be added.
   */
  abstract fun addViewForModel(position: Int, model: T)
}
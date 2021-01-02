package com.cerridan.dndxpcalc.adapter.result

import androidx.annotation.StringRes

/**
 * Epoxy model for the ResultFragment.
 *
 * @see ResultEpoxyController
 * @see com.cerridan.dndxpcalc.fragment.ResultFragment
 *
 * @author Brian
 * @since December 31st, 2020
 */
sealed class ResultEpoxyModel(val id: String) {

  /**
   * Header item.
   *
   * @see com.cerridan.dndxpcalc.ui.HeaderItemView
   * @param header header text resource to display.
   */
  class HeaderItem(@StringRes val header: Int) : ResultEpoxyModel(header.toString())

  /**
   * Key-value item for displaying results.
   *
   * @see com.cerridan.dndxpcalc.ui.ResultItemView
   */
  class ResultItem(@StringRes val title: Int, val value: String) : ResultEpoxyModel(title.toString())
}

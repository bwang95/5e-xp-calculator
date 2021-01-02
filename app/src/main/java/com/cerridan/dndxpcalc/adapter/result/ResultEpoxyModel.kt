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
class ResultEpoxyModel(
  @StringRes val title: Int,
  val value: String
)

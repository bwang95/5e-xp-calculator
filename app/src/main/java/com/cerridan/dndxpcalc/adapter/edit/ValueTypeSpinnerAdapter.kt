package com.cerridan.dndxpcalc.adapter.edit

import android.content.Context
import com.cerridan.dndxpcalc.adapter.BaseTextViewAdapter
import com.cerridan.dndxpcalc.model.ValueType

/**
 * Adapter for the value type spinner for the edit dialog.
 *
 * @see com.cerridan.dndxpcalc.ui.EditEntityDialog
 * @see com.cerridan.dndxpcalc.ui.EditEntityDialogView
 *
 * @author Brian
 * @since December 31st, 2020
 */
class ValueTypeSpinnerAdapter(
  private val context: Context
) : BaseTextViewAdapter<ValueType>(
  context = context,
  itemLayout = android.R.layout.simple_list_item_1
) {

  override fun getStringForItem(item: ValueType): String = context.getString(item.description)

  override fun getIdForItem(item: ValueType): Long = item.description.toLong()
}
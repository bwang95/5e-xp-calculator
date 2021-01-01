package com.cerridan.dndxpcalc.adapter.calc

import android.content.Context
import com.cerridan.dndxpcalc.adapter.BaseTextViewAdapter
import com.cerridan.dndxpcalc.model.ValueType

class ValueTypeSpinnerAdapter(
  private val context: Context
) : BaseTextViewAdapter<ValueType>(context, android.R.layout.simple_list_item_1) {
  override fun getStringForItem(item: ValueType): String = context.getString(item.description)

  override fun getIdForItem(item: ValueType): Long = item.description.toLong()
}
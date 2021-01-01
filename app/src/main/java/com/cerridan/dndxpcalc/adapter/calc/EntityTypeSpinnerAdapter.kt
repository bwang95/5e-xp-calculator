package com.cerridan.dndxpcalc.adapter.calc

import android.content.Context
import com.cerridan.dndxpcalc.adapter.BaseTextViewAdapter
import com.cerridan.dndxpcalc.model.EntityType

class EntityTypeSpinnerAdapter(
  private val context: Context
) : BaseTextViewAdapter<EntityType>(context, android.R.layout.simple_list_item_1) {
  override fun getStringForItem(item: EntityType): String = context.getString(item.header)

  override fun getIdForItem(item: EntityType): Long = item.header.toLong()
}
package com.cerridan.dndxpcalc.adapter.edit

import android.content.Context
import com.cerridan.dndxpcalc.adapter.BaseTextViewAdapter
import com.cerridan.dndxpcalc.model.EntityType

/**
 * Adapter for the entity type spinner for the edit dialog.
 *
 * @see com.cerridan.dndxpcalc.ui.EditEntityDialog
 * @see com.cerridan.dndxpcalc.ui.EditEntityDialogView
 *
 * @author Brian
 * @since December 31st, 2020
 */
class EntityTypeSpinnerAdapter(
  private val context: Context
) : BaseTextViewAdapter<EntityType>(
  context = context,
  itemLayout = android.R.layout.simple_list_item_1
) {

  override fun getStringForItem(item: EntityType): String = context.getString(item.header)

  override fun getIdForItem(item: EntityType): Long = item.header.toLong()
}
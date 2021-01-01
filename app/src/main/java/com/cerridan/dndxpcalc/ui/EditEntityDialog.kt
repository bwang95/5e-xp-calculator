package com.cerridan.dndxpcalc.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface.BUTTON_POSITIVE
import android.view.LayoutInflater
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.model.CalcEntity

class EditEntityDialog(
  private val context: Context,
  private val initialEntity: CalcEntity?
) {
  private var addListener: ((CalcEntity) -> Unit) = {}
  private var deleteListener: ((CalcEntity) -> Unit) = {}

  fun setAddListener(listener: (CalcEntity) -> Unit) {
    addListener = listener
  }

  fun setDeleteListener(listener: (CalcEntity) -> Unit) {
    deleteListener = listener
  }

  fun show() {
    val view = LayoutInflater.from(context)
      .inflate(R.layout.dialog_edit_entity, null, false)
      as EditEntityDialogView

    val (title, positiveText, shouldShowDelete) = if (initialEntity != null) {
      Triple(R.string.edit_dialog_edit, R.string.edit_dialog_ok, true)
    } else {
      Triple(R.string.edit_dialog_add, R.string.edit_dialog_action_add, false)
    }

    var builder = AlertDialog.Builder(context)
      .setTitle(title)
      .setView(view)
      .setPositiveButton(positiveText) { dialog, _ ->
        view.entity?.let(addListener)
        dialog.dismiss()
      }
      .setNegativeButton(R.string.edit_dialog_cancel) { dialog, _ -> dialog.dismiss() }

    if (shouldShowDelete) {
      builder = builder.setNeutralButton(R.string.edit_dialog_delete) { dialog, _ ->
        initialEntity?.let(deleteListener)
        dialog.dismiss()
      }
    }

    val dialog = builder.create()
    view.setValidateListener { dialog.getButton(BUTTON_POSITIVE).isEnabled = it }
    dialog.show()
    initialEntity?.let(view::bind)
    dialog.getButton(BUTTON_POSITIVE).isEnabled = view.isValid
  }
}
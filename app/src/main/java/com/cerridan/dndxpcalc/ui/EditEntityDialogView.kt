package com.cerridan.dndxpcalc.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.adapter.calc.EntityTypeSpinnerAdapter
import com.cerridan.dndxpcalc.adapter.calc.ValueTypeSpinnerAdapter
import com.cerridan.dndxpcalc.model.CalcEntity
import com.cerridan.dndxpcalc.model.EntityType
import com.cerridan.dndxpcalc.model.EntityType.CHARACTER
import com.cerridan.dndxpcalc.model.ValueType
import com.cerridan.dndxpcalc.util.bindView
import com.jakewharton.rxbinding4.widget.itemSelections
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

class EditEntityDialogView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
  private val typeSpinner: Spinner by bindView(R.id.sp_edit_dialog_type)
  private val quantityEditText: EditText by bindView(R.id.et_edit_dialog_quantity)
  private val valueTypeSpinner: Spinner by bindView(R.id.sp_edit_dialog_value_type)
  private val valueEditText: EditText by bindView(R.id.et_edit_dialog_value)

  private val disposables = CompositeDisposable()
  private val typeValues = EntityType.values().toList()

  private lateinit var typeAdapter: EntityTypeSpinnerAdapter
  private lateinit var valueTypeAdapter: ValueTypeSpinnerAdapter

  private var validateListener: (Boolean) -> Unit = {}
  val isValid: Boolean
    get() = valueEditText.text.isNotBlank() &&
        quantityEditText.text.isNotBlank() &&
        valueTypeSpinner.selectedItemPosition > -1 &&
        typeSpinner.selectedItemPosition > -1
  val entity: CalcEntity?
    get() = if (!isValid) {
      null
    } else {
      CalcEntity(
        type = typeSpinner.selectedItem as EntityType,
        quantity = quantityEditText.text.toString().toInt(),
        valueType = valueTypeSpinner.selectedItem as ValueType,
        value = valueEditText.text.toString().toInt()
      )
    }

  override fun onFinishInflate() {
    super.onFinishInflate()

    typeAdapter = EntityTypeSpinnerAdapter(context).apply { setData(typeValues) }
    valueTypeAdapter = ValueTypeSpinnerAdapter(context)

    typeSpinner.adapter = typeAdapter
    valueTypeSpinner.adapter = valueTypeAdapter
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()

    Observable.merge(
      quantityEditText.textChanges(),
      valueEditText.textChanges(),
      valueTypeSpinner.itemSelections(),
      typeSpinner.itemSelections()
        .startWithItem(0)
        .doOnNext { updateValueTypeAdapter() }
    )
      .observeOn(mainThread())
      .subscribe { validateListener(isValid) }
      .let(disposables::add)
  }

  override fun onDetachedFromWindow() {
    disposables.clear()
    super.onDetachedFromWindow()
  }

  fun setValidateListener(listener: (Boolean) -> Unit) {
    validateListener = listener
  }

  fun bind(entity: CalcEntity) {
    typeSpinner.setSelection(typeValues.indexOf(entity.type))
    updateValueTypeAdapter()
    valueTypeSpinner.setSelection(entity.type.validValueTypes.indexOf(entity.valueType))
    quantityEditText.setText(entity.quantity.toString())
    valueEditText.setText(entity.value.toString())
  }

  private fun updateValueTypeAdapter() {
    val validTypes = ((typeSpinner.selectedItem as? EntityType) ?: CHARACTER).validValueTypes
    valueTypeAdapter.setData(validTypes)
  }
}
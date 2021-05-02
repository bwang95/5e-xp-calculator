package com.cerridan.dndxpcalc.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.adapter.edit.EntityTypeSpinnerAdapter
import com.cerridan.dndxpcalc.adapter.edit.ValueTypeSpinnerAdapter
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
import io.reactivex.rxjava3.disposables.SerialDisposable
import java.util.UUID

/**
 * Custom view for the [EditEntityDialog].
 * Allows the user to enter Entity Type, Entity Quantity,
 * and different types of valid values.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class EditEntityDialogView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val typeSpinner: Spinner by bindView(R.id.sp_edit_dialog_type)
  private val quantityEditText: EditText by bindView(R.id.et_edit_dialog_quantity)
  private val valueTypeSpinner: Spinner by bindView(R.id.sp_edit_dialog_value_type)
  private val valueEditText: EditText by bindView(R.id.et_edit_dialog_value)

  private val typeValues = EntityType.values().toList()
  private val validateDisposable = SerialDisposable()

  private lateinit var typeAdapter: EntityTypeSpinnerAdapter
  private lateinit var valueTypeAdapter: ValueTypeSpinnerAdapter

  private var entityId = ""

  /**
   * A listener for validation events.
   * Called with true when the view obtains enough valid data to construct a [CalcEntity],
   * and false when that is no longer the case.
   */
  private var validateListener: (Boolean) -> Unit = {}

  /**
   * @return true if this view has enough valid data to construct a [CalcEntity], false otherwise.
   */
  val isValid: Boolean
    get() = valueEditText.text.isNotBlank() &&
            quantityEditText.text.isNotBlank() &&
            valueTypeSpinner.selectedItemPosition > -1 &&
            typeSpinner.selectedItemPosition > -1

  /**
   * @return a [CalcEntity] constructed from the data in this view,
   *         null if there is insufficient data.
   */
  val entity: CalcEntity?
    get() = if (!isValid) {
      null
    } else {
      CalcEntity(
        id = entityId.takeIf(String::isNotBlank) ?: UUID.randomUUID().toString(),
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
      .let(validateDisposable::set)
  }

  override fun onDetachedFromWindow() {
    validateDisposable.set(null)
    super.onDetachedFromWindow()
  }

  /**
   * Sets a validation listener on this view.
   *
   * @see validateListener
   */
  fun setValidateListener(listener: (Boolean) -> Unit) {
    validateListener = listener
  }

  /** Sets the data in [CalcEntity] to this view's fields. */
  fun bind(entity: CalcEntity) {
    entityId = entity.id
    typeSpinner.setSelection(typeValues.indexOf(entity.type))
    updateValueTypeAdapter()
    valueTypeSpinner.setSelection(entity.type.validValueTypes.indexOf(entity.valueType))
    quantityEditText.setText(entity.quantity.toString())
    valueEditText.setText(entity.value.toString())
  }

  /** Updates the [valueTypeAdapter] with valid values for the given [EntityType]. */
  private fun updateValueTypeAdapter() {
    val validTypes = ((typeSpinner.selectedItem as? EntityType) ?: CHARACTER).validValueTypes
    valueTypeAdapter.setData(validTypes)
  }
}
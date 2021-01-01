package com.cerridan.dndxpcalc.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.adapter.calc.CalculatorCallbacks
import com.cerridan.dndxpcalc.model.CalcEntity
import com.cerridan.dndxpcalc.util.bindView

@ModelView(defaultLayout = R.layout.item_entity)
class EntityItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
  private val quantityView: TextView by bindView(R.id.tv_entity_item_quantity)
  private val valueTypeView: TextView by bindView(R.id.tv_entity_item_value_type)
  private val valueView: TextView by bindView(R.id.tv_entity_item_value)

  private var callbacks: CalculatorCallbacks? = null

  @ModelProp fun setEntity(entity: CalcEntity) {
    quantityView.text = entity.quantity.toString()
    valueView.text = String.format("%,d", entity.value)
    valueTypeView.setText(entity.valueType.description)

    setOnClickListener { callbacks?.onEntityItemClicked(entity) }
  }

  @CallbackProp fun setCallbacks(callbacks: CalculatorCallbacks?) {
    this.callbacks = callbacks
  }
}
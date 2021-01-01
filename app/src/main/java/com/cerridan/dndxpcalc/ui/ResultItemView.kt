package com.cerridan.dndxpcalc.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.util.bindView

@ModelView(defaultLayout = R.layout.item_result)
class ResultItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
  private val titleView: TextView by bindView(R.id.tv_result_item_title)
  private val valueView: TextView by bindView(R.id.tv_result_item_value)

  @ModelProp fun setTitle(@StringRes title: Int) {
    titleView.setText(title)
  }

  @ModelProp fun setValue(value: String) {
    valueView.text = value
  }
}
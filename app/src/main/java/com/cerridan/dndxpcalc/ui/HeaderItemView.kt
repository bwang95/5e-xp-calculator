package com.cerridan.dndxpcalc.ui

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.cerridan.dndxpcalc.R

/**
 * Simple header view.
 *
 * @author Brian
 * @since December 31st, 2020
 */
@ModelView(defaultLayout = R.layout.item_header)
class HeaderItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

  @ModelProp fun setHeader(@StringRes header: Int) {
    setText(header)
  }
}
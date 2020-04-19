package com.cerridan.androidtemplate.ui.listitem.home

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.cerridan.androidtemplate.R
import com.cerridan.androidtemplate.adapter.home.HomeCallbacks
import com.cerridan.androidtemplate.util.bindView

@ModelView(defaultLayout = R.layout.list_item_home)
class HomeListItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val titleView: TextView by bindView(R.id.tv_home_item_title)
  private val dataView: TextView by bindView(R.id.tv_home_item_data)

  private var callbacks: HomeCallbacks? = null

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    setOnClickListener { callbacks?.onHomeListItemClicked(dataView.text?.toString() ?: "") }
  }

  override fun onDetachedFromWindow() {
    setOnClickListener(null)
    super.onDetachedFromWindow()
  }

  @ModelProp
  fun setTitle(title: String) { titleView.text = title }

  @ModelProp
  fun setData(data: String) { dataView.text = data }

  @CallbackProp
  fun setHomeCallbacks(callbacks: HomeCallbacks?) { this.callbacks = callbacks }
}
package com.cerridan.dndxpcalc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

/**
 * @param itemLayout Layout resource of a layout which _must_ extend TextView.
 */
abstract class BaseTextViewAdapter<T>(
  context: Context,
  @LayoutRes val itemLayout: Int
) : BaseAdapter() {
  private val inflater by lazy { LayoutInflater.from(context) }
  private val data: MutableList<T> = mutableListOf()

  fun setData(data: List<T>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  override fun getCount(): Int = data.size

  override fun getItem(position: Int): T = data[position]

  override fun getItemId(position: Int): Long = getIdForItem(getItem(position))

  abstract fun getStringForItem(item: T): String

  abstract fun getIdForItem(item: T): Long

  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view = (convertView as? TextView)
      ?: inflater.inflate(itemLayout, parent, false) as TextView
    view.text = getStringForItem(getItem(position))
    return view
  }
}
package com.cerridan.dndxpcalc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

/**
 * Basic typed adapter which handles inflating and displaying [TextView]s.
 * Wraps adapter functionality into [getStringForItem] and [getIdForItem] methods,
 * which take items of type [T] instead of position.
 *
 * @throws IllegalStateException if [itemLayout] does not describe a [TextView].
 * @param itemLayout Layout resource of a layout which _must_ either be or extend [TextView].
 *
 * @author Brian
 * @since December 31st, 2020
 */
abstract class BaseTextViewAdapter<T>(
  context: Context,
  @LayoutRes val itemLayout: Int
) : BaseAdapter() {

  private val inflater by lazy { LayoutInflater.from(context) }
  private val data: MutableList<T> = mutableListOf()

  init {
    check(inflater.inflate(itemLayout, null, false) is TextView) {
      "Layout $itemLayout passed to ${BaseTextViewAdapter::class.simpleName} was not a TextView"
    }
  }

  // BaseAdapter methods
  final override fun getCount(): Int = data.size

  final override fun getItem(position: Int): T = data[position]

  final override fun getItemId(position: Int): Long = getIdForItem(getItem(position))

  final override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    val view = (convertView as? TextView)
      ?: inflater.inflate(itemLayout, parent, false) as TextView
    view.text = getStringForItem(getItem(position))
    return view
  }

  /**
   * Replaces the data currently in this adapter and
   * refreshes the adapter via [notifyDataSetChanged].
   */
  fun setData(data: List<T>) {
    this.data.clear()
    this.data.addAll(data)
    notifyDataSetChanged()
  }

  /** @return the [String] to display for the given [item]. */
  abstract fun getStringForItem(item: T): String

  /** @return a unique [Long] ID for the given [item]. */
  abstract fun getIdForItem(item: T): Long
}
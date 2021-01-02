package com.cerridan.dndxpcalc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Base [Fragment] class which provides convenience functionality,
 * such as inflating the view and obtaining a ViewModel.
 *
 * @param layout The fragment's layout, inflated in [onCreateView].
 * @param viewModelClass The [Class] of the [ViewModel].
 *
 * @author Brian
 * @since December 31st, 2020
 */
open class BaseFragment<VM : ViewModel>(
  @LayoutRes private val layout: Int,
  private val viewModelClass: Class<VM>
) : Fragment() {

  /**
   * The [ViewModel] associated with this [BaseFragment].
   * Initialized in [onViewCreated].
   */
  protected lateinit var viewModel: VM

  final override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = inflater.inflate(layout, container, false)

  @CallSuper
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel = ViewModelProvider(this).get(viewModelClass)
  }
}
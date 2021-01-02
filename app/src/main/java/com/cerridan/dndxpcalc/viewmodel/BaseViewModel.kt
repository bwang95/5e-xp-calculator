package com.cerridan.dndxpcalc.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Provides a simple extension on [AndroidViewModel]
 * which makes the Application [Context] and [Resources]
 * available to subclasses, as well as handling subscription management
 * via a [CompositeDisposable].
 *
 * @author Brian
 * @since December 31st, 2020
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
  /** The Application Context. */
  protected val appContext: Context get() = getApplication<Application>()

  /** Resources, derived from the Application Context. */
  protected val resources: Resources get() = appContext.resources

  /** Composite Disposable, cleared in [onCleared]. */
  protected val disposables = CompositeDisposable()

  @CallSuper override fun onCleared() {
    disposables.clear()
    super.onCleared()
  }
}

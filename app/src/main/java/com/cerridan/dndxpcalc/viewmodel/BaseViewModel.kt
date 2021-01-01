package com.cerridan.dndxpcalc.viewmodel

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
  protected val appContext: Context get() = getApplication<Application>()
  protected val resources: Resources get() = appContext.resources

  protected val disposables = CompositeDisposable()

  @CallSuper
  override fun onCleared() {
    disposables.clear()
    super.onCleared()
  }
}
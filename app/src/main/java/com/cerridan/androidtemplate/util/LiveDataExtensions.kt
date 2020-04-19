package com.cerridan.androidtemplate.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, onNext: (T) -> Unit) =
  observe(lifecycleOwner, Observer(onNext))
package com.cerridan.dndxpcalc.util

class DiscreteEvent<T>(private var data: T?) {
  fun consumeEvent(): T? = data?.also { data = null }
}
package com.cerridan.dndxpcalc.util

/**
 * Consumable event which only allows its data to be used once.
 * Used to prevent LiveData from duplicating calls.
 *
 * @param data the data that this event should pass.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class DiscreteEvent<T>(data: T) {
  private var data: T? = data

  /** Consumes the data in [data] and returns it. */
  fun consumeEvent(): T? = data?.also { data = null }
}

package com.cerridan.dndxpcalc.util

import androidx.annotation.StringRes

/**
 * Exception used by the XPCalculator to indicate invalid input.
 * Provides a localizable user-facing message and a debug-friendly message.
 *
 * @param userMessage A string resource that should be displayed to the user.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class CalculationException(
  @StringRes val userMessage: Int,
  message: String
) : Exception(message)

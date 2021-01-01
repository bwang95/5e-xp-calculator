package com.cerridan.dndxpcalc.util

import androidx.annotation.StringRes

class CalculationException(
  @StringRes val userMessage: Int,
  message: String
) : Exception(message)

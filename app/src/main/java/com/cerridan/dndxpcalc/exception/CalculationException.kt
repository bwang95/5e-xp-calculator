package com.cerridan.dndxpcalc.exception

import androidx.annotation.StringRes

class CalculationException(
  @StringRes val userMessage: Int,
  message: String
) : Exception(message)

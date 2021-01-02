package com.cerridan.dndxpcalc

import android.app.Application
import com.cerridan.dndxpcalc.model.manager.RulesetManager
import com.cerridan.dndxpcalc.model.manager.SavedStateManager

/**
 * Application class.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class XPCalcApp : Application() {

  override fun onCreate() {
    super.onCreate()

    // Initialize file readers, which need app context
    SavedStateManager.init(this)
    RulesetManager.init(this)
  }
}
package com.cerridan.dndxpcalc

import android.app.Application
import com.cerridan.dndxpcalc.model.manager.RulesetManager
import com.cerridan.dndxpcalc.model.manager.SavedStateManager

class XPCalcApp : Application() {
  override fun onCreate() {
    super.onCreate()

    SavedStateManager.init(this)
    RulesetManager.init(this)
  }
}
package com.cerridan.dndxpcalc

import android.app.Application
import com.cerridan.dndxpcalc.model.util.EntityFileWriter

class XPCalcApp : Application() {
  override fun onCreate() {
    super.onCreate()

    EntityFileWriter.init(this)
  }
}
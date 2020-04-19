package com.cerridan.androidtemplate

import android.app.Application
import com.cerridan.androidtemplate.model.DependencyGraph

class ExampleApp : Application() {
  override fun onCreate() {
    super.onCreate()

    DependencyGraph.initialize(this)
  }
}
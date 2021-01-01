package com.cerridan.dndxpcalc.model.manager

import android.content.Context
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.model.Ruleset
import java.io.BufferedReader

object RulesetManager {
  private lateinit var mutableRuleset: Ruleset

  val ruleset: Ruleset get() = mutableRuleset

  fun init(context: Context) {
    mutableRuleset = context.resources
      .openRawResource(R.raw.ruleset)
      .bufferedReader(Charsets.UTF_8)
      .use(BufferedReader::readText)
      .let { MoshiWrapper.deserialize(it, Ruleset::class.java)!! }
  }
}
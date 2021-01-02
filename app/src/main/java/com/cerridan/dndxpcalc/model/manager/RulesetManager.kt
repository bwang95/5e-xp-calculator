package com.cerridan.dndxpcalc.model.manager

import android.content.Context
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.model.Ruleset
import java.io.BufferedReader

/**
 * Manages reading and writing [Ruleset] objects from the packaged JSON file.
 *
 * @author Brian
 * @since December 31st, 2020
 */
object RulesetManager {

  private lateinit var mutableRuleset: Ruleset
  val ruleset: Ruleset get() = mutableRuleset

  /**
   * Reads the ruleset file into memory.
   */
  fun init(context: Context) {
    mutableRuleset = context.resources
      .openRawResource(R.raw.ruleset)
      .bufferedReader(Charsets.UTF_8)
      .use(BufferedReader::readText)
      .let { MoshiWrapper.deserialize(it, Ruleset::class.java)!! }
  }
}
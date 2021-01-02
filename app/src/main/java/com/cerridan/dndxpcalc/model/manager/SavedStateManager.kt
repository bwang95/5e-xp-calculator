package com.cerridan.dndxpcalc.model.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.cerridan.dndxpcalc.model.CalcEntityList

/**
 * Manages reading and writing the saved state in the form of a [CalcEntityList].
 *
 * @author Brian
 * @since December 31st, 2020
 */
object SavedStateManager {

  /** Shared preference instance key for saved state. */
  private const val PREFS_NAME = "com.cerridan.dndxpcalc#saved_entities_state"

  /** Shared preference key for the entity list. */
  private const val ENTITY_LIST_KEY = "com.cerridan.dndxpcalc#entity_list"

  private lateinit var preferences: SharedPreferences

  /** Initializes the [SharedPreferences] instance. */
  fun init(context: Context) {
    preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
  }

  /** Writes the provided [CalcEntityList] to [SharedPreferences]. */
  fun saveToSharedPreferences(entities: CalcEntityList) {
    preferences.edit()
        .putString(ENTITY_LIST_KEY, MoshiWrapper.serialize(entities, CalcEntityList::class.java))
        .apply()
  }

  /**
   * Reads the [CalcEntityList] from [SharedPreferences].
   *
   * @return a valid [CalcEntityList] if it exists, null if not.
   */
  fun readFromSharedPreferences(): CalcEntityList? {
    return preferences.getString(ENTITY_LIST_KEY, "")
        ?.takeIf(String::isNotBlank)
        ?.let { MoshiWrapper.deserialize(it, CalcEntityList::class.java) }
  }
}
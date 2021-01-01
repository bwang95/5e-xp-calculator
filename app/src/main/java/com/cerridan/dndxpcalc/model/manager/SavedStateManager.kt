package com.cerridan.dndxpcalc.model.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.cerridan.dndxpcalc.model.CalcEntityList

object SavedStateManager {
  private const val PREFS_NAME = "com.cerridan.dndxpcalc#saved_entities_state"
  private const val ENTITY_LIST_KEY = "com.cerridan.dndxpcalc#entity_list"

  private lateinit var preferences: SharedPreferences

  fun init(context: Context) {
    preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
  }

  fun saveToSharedPreferences(entities: CalcEntityList) {
    preferences.edit()
        .putString(ENTITY_LIST_KEY, MoshiWrapper.serialize(entities, CalcEntityList::class.java))
        .apply()
  }

  fun readFromSharedPreferences(): CalcEntityList? {
    return preferences.getString(ENTITY_LIST_KEY, "")
        ?.takeIf(String::isNotBlank)
        ?.let { MoshiWrapper.deserialize(it, CalcEntityList::class.java) }
  }
}
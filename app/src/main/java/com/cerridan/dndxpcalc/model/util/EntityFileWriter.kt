package com.cerridan.dndxpcalc.model.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.cerridan.dndxpcalc.model.CalcEntityList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object EntityFileWriter {
  private const val PREFS_NAME = "com.cerridan.dndxpcalc#saved_entities_state"
  private const val ENTITY_LIST_KEY = "com.cerridan.dndxpcalc#entity_list"

  private val moshi = Moshi.Builder()
      .add(DateAdapterFactory())
      .add(KotlinJsonAdapterFactory())
      .build()

  private lateinit var preferences: SharedPreferences

  fun init(context: Context) {
    preferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
  }

  fun saveToSharedPreferences(entities: CalcEntityList) {
    val json = moshi
        .adapter(CalcEntityList::class.java)
        .toJson(entities)

    preferences.edit()
        .putString(ENTITY_LIST_KEY, json)
        .apply()
  }

  fun readFromSharedPreferences(): CalcEntityList? {
    return preferences.getString(ENTITY_LIST_KEY, "")
        ?.takeIf(String::isNotBlank)
        ?.let { moshi.adapter(CalcEntityList::class.java).fromJson(it) }
  }
}
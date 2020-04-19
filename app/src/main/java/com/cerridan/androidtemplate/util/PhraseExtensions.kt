package com.cerridan.androidtemplate.util

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import com.squareup.phrase.Phrase

fun Context.formatPhrase(@StringRes resId: Int, formatter: Phrase.() -> Unit): CharSequence =
  resources.formatPhrase(resId, formatter)

fun Resources.formatPhrase(@StringRes resId: Int, formatter: Phrase.() -> Unit): CharSequence =
  Phrase.from(this, resId).apply { formatter() }.format()
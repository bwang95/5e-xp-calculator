package com.cerridan.androidtemplate.adapter.home

sealed class HomeEpoxyModel(val id: String) {
  class ListItem(val title: String, val data: String) : HomeEpoxyModel(data)
}
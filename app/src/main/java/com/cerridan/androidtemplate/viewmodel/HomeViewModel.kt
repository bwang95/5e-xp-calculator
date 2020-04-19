package com.cerridan.androidtemplate.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cerridan.androidtemplate.R
import com.cerridan.androidtemplate.adapter.home.HomeEpoxyModel
import com.cerridan.androidtemplate.adapter.home.HomeEpoxyModel.ListItem
import com.cerridan.androidtemplate.model.DependencyGraph
import com.cerridan.androidtemplate.util.Result.Failure
import com.cerridan.androidtemplate.util.Result.Success

class HomeViewModel(application: Application) : BaseViewModel(application) {
  private val _epoxyModels = MutableLiveData<List<HomeEpoxyModel>>()
  val epoxyModels: LiveData<List<HomeEpoxyModel>> = _epoxyModels

  fun refresh() {
    DependencyGraph.exampleService
      .getExampleResponse()
      .subscribe { result ->
        val models: List<HomeEpoxyModel> = when (result) {
          is Success ->
            listOf(ListItem(resources.getString(R.string.home_list_title), result.value.exampleField))
          is Failure -> listOf(ListItem(
            resources.getString(R.string.home_list_title),
            resources.getString(R.string.home_list_error)
          ))
        }
        _epoxyModels.postValue(models)
      }
      .let(disposables::add)
  }
}
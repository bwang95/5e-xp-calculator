package com.cerridan.androidtemplate.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cerridan.androidtemplate.R
import com.cerridan.androidtemplate.adapter.home.HomeCallbacks
import com.cerridan.androidtemplate.adapter.home.HomeEpoxyController
import com.cerridan.androidtemplate.util.bindView
import com.cerridan.androidtemplate.util.observe
import com.cerridan.androidtemplate.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel>(
  layout = R.layout.fragment_home,
  viewModelClass = HomeViewModel::class.java
), HomeCallbacks {
  private val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swl_home_container)
  private val recyclerView: RecyclerView by bindView(R.id.rv_home_recycler)

  private val epoxyController = HomeEpoxyController(this)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = epoxyController.adapter

    swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

    viewModel.epoxyModels.observe(viewLifecycleOwner) { models ->
      swipeRefreshLayout.isRefreshing = false
      epoxyController.setData(models)
    }
}

  override fun onResume() {
    super.onResume()

    viewModel.refresh()
  }

  override fun onHomeListItemClicked(data: String) {
    Toast.makeText(context, data, LENGTH_SHORT).show()
  }
}
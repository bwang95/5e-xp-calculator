package com.cerridan.dndxpcalc.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.adapter.result.ResultEpoxyController
import com.cerridan.dndxpcalc.model.CalcResult
import com.cerridan.dndxpcalc.model.manager.MoshiWrapper
import com.cerridan.dndxpcalc.util.bindView
import com.cerridan.dndxpcalc.viewmodel.ResultViewModel

class ResultFragment : BaseFragment<ResultViewModel>(
  layout = R.layout.fragment_result,
  viewModelClass = ResultViewModel::class.java
) {
  companion object {
    private const val RESULT_KEY = "com.cerridan.dndxpcalc#serialized_result"

    fun create(serializedResult: String) = ResultFragment().apply {
      val newArgs = (arguments ?: Bundle())
      newArgs.putString(RESULT_KEY, serializedResult)
      arguments = newArgs
    }
  }

  private val resultRecycler: RecyclerView by bindView(R.id.rv_result_recycler)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val result = MoshiWrapper.deserialize(
      arguments?.getString(RESULT_KEY) ?: "",
      CalcResult::class.java
    )

    viewModel.onCreate(result!!)

    val epoxyController = ResultEpoxyController()
    resultRecycler.layoutManager = LinearLayoutManager(context)
    resultRecycler.adapter = epoxyController.adapter
    resultRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    viewModel.models.observe(viewLifecycleOwner, epoxyController::setData)
  }
}
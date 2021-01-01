package com.cerridan.dndxpcalc.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.MainActivity
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyController
import com.cerridan.dndxpcalc.ui.EditEntityDialog
import com.cerridan.dndxpcalc.util.bindView
import com.cerridan.dndxpcalc.viewmodel.CalculatorViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CalculatorFragment : BaseFragment<CalculatorViewModel>(
    layout = R.layout.fragment_calculator,
    viewModelClass = CalculatorViewModel::class.java
) {
  private val recyclerView: RecyclerView by bindView(R.id.rv_calculator_recycler)
  private val addButton: View by bindView(R.id.fab_calculator_add)
  private val calculateButton: FloatingActionButton by bindView(R.id.fab_calculator_calculate)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.onCreate()

    val controller = CalculatorEpoxyController(viewModel)

    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = controller.adapter
    recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    addButton.setOnClickListener { viewModel.onAddEntityClicked() }
    calculateButton.setOnClickListener { viewModel.onCalculateClicked() }

    viewModel.entityModels.observe(viewLifecycleOwner, controller::setData)
    viewModel.calculate.observe(viewLifecycleOwner) {
      val data = it.consumeEvent()
      if (data != null) {
        (activity as? MainActivity)?.showResult(data)
      }
    }
    viewModel.messages.observe(viewLifecycleOwner) {
      Toast.makeText(context, it, LENGTH_LONG).show()
    }
    viewModel.canCalculate.observe(viewLifecycleOwner) { shouldShowButton ->
      if (shouldShowButton) calculateButton.show() else calculateButton.hide()
    }
    viewModel.editEntity.observe(viewLifecycleOwner) { entity ->
      val dialog = EditEntityDialog(view.context, entity)
      dialog.setAddListener(viewModel::onAddOrEditEntity)
      dialog.setDeleteListener { viewModel.onRemoveEntity(it.id) }
      dialog.show()
    }
  }

  override fun onStop() {
    viewModel.onStop()
    super.onStop()
  }
}
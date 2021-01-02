package com.cerridan.dndxpcalc.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
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

/**
 * Fragment responsible for calculation setup.
 * The user can add different types of entities to a list before calculating.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class CalculatorFragment : BaseFragment<CalculatorViewModel>(
    layout = R.layout.fragment_calculator,
    viewModelClass = CalculatorViewModel::class.java
) {
  private val recyclerView: RecyclerView by bindView(R.id.rv_calculator_recycler)
  private val addButton: View by bindView(R.id.fab_calculator_add)
  private val calculateButton: FloatingActionButton by bindView(R.id.fab_calculator_calculate)

  private lateinit var epoxyController: CalculatorEpoxyController

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.onCreate()
    epoxyController = CalculatorEpoxyController(viewModel)

    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = epoxyController.adapter
    recyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))

    addButton.setOnClickListener { viewModel.onAddEntityClicked() }
    calculateButton.setOnClickListener { viewModel.onCalculateClicked() }

    setupObservers(view)
  }

  private fun setupObservers(view: View) {
    viewModel.entityModels.observe(viewLifecycleOwner, Observer(epoxyController::setData))

    viewModel.messages.observe(viewLifecycleOwner) { Toast.makeText(context, it, LENGTH_LONG).show() }

    viewModel.calculate.observe(viewLifecycleOwner) { event ->
      event.consumeEvent()?.let { (activity as? MainActivity)?.showResult(it) }
    }

    viewModel.canCalculate.observe(viewLifecycleOwner) { shouldShowButton ->
      if (shouldShowButton) calculateButton.show() else calculateButton.hide()
    }

    viewModel.editEntity.observe(viewLifecycleOwner) { entity ->
      EditEntityDialog(view.context, entity)
        .setAddListener(viewModel::onAddOrEditEntity)
        .setDeleteListener { viewModel.onRemoveEntity(it.id) }
        .show()
    }
  }

  override fun onStop() {
    viewModel.onStop()
    super.onStop()
  }
}
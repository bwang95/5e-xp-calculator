package com.cerridan.dndxpcalc.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cerridan.dndxpcalc.R
import com.cerridan.dndxpcalc.adapter.result.ResultEpoxyModel
import com.cerridan.dndxpcalc.model.CalcResult

/**
 * ViewModel for the Result Fragment.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class ResultViewModel(application: Application) : BaseViewModel(application) {
  companion object {
    /** Titles for the difficulty thresholds. */
    private val THRESHOLD_TITLES = listOf(
      R.string.result_threshold_easy,
      R.string.result_threshold_medium,
      R.string.result_threshold_hard,
      R.string.result_threshold_deadly
    )
  }

  private val mutableModels = MutableLiveData<List<ResultEpoxyModel>>()
  /** Epoxy models for the recycler view. */
  val models: LiveData<List<ResultEpoxyModel>> = mutableModels

  /** Called when the view is created. Constructs Epoxy Models. */
  fun onCreate(result: CalcResult) {
    val difficulty = appContext.getString(THRESHOLD_TITLES[result.thresholdIdx])
    val newModels = mutableListOf(ResultEpoxyModel(R.string.result_encounter_difficulty, difficulty))
    newModels += THRESHOLD_TITLES.mapIndexed { idx, title ->
      ResultEpoxyModel(title, String.format("%,d", result.encounterThresholds[idx]))
    }
    newModels += listOf(
      ResultEpoxyModel(R.string.result_monster_xp, String.format("%,d", result.monsterXp)),
      ResultEpoxyModel(R.string.result_divided_monster_xp, String.format("%,d", result.dividedMonsterXp)),
      ResultEpoxyModel(R.string.result_adjusted_xp, String.format("%,d", result.adjustedXp)),
      ResultEpoxyModel(R.string.result_divided_adjusted_xp, String.format("%,d", result.dividedAdjustedXp)),
    )
    mutableModels.postValue(newModels)
  }
}
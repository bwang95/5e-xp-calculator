package com.cerridan.dndxpcalc.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cerridan.dndxpcalc.adapter.calc.CalculatorCallbacks
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel.EntityItem
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel.HeaderItem
import com.cerridan.dndxpcalc.util.CalculationException
import com.cerridan.dndxpcalc.model.CalcEntity
import com.cerridan.dndxpcalc.model.CalcEntityList
import com.cerridan.dndxpcalc.model.CalcResult
import com.cerridan.dndxpcalc.model.EntityType
import com.cerridan.dndxpcalc.model.EntityType.CHARACTER
import com.cerridan.dndxpcalc.model.EntityType.MONSTER
import com.cerridan.dndxpcalc.model.manager.MoshiWrapper
import com.cerridan.dndxpcalc.model.manager.SavedStateManager
import com.cerridan.dndxpcalc.model.util.XPCalculator
import com.cerridan.dndxpcalc.util.DiscreteEvent
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Date

/**
 * ViewModel for the Calculator Fragment.
 *
 * @author Brian
 * @since December 31st, 2020
 */
class CalculatorViewModel(
  application: Application
) : BaseViewModel(application), CalculatorCallbacks {
  companion object {
    /** Comparator for [CalcEntity] objects for list display. */
    private val entityComparator = compareBy(
        { it.type.ordinal },
        { it.valueType.ordinal },
        CalcEntity::value,
        CalcEntity::quantity,
        CalcEntity::id
    )
  }

  private val mutableMessages = MutableLiveData<String>()
  /** Messages for Toast. */
  val messages: LiveData<String> get() = mutableMessages

  private val mutableCanCalculate = MutableLiveData<Boolean>()
  /** Whether the calculate button should be shown. */
  val canCalculate: LiveData<Boolean> get() = mutableCanCalculate

  private val mutableCalculate = MutableLiveData<DiscreteEvent<CalcResult>>()
  /** The app should move to the Result Fragment with the given [CalcResult]. */
  val calculate: LiveData<DiscreteEvent<CalcResult>> get() = mutableCalculate

  private val mutableEntityModels = MutableLiveData<List<CalculatorEpoxyModel>>()
  /** Epoxy Models for the recycler view. */
  val entityModels: LiveData<List<CalculatorEpoxyModel>> get() = mutableEntityModels

  private val mutableEditEntity = MutableLiveData<CalcEntity?>()
  /** The Edit Entity Dialog should be shown, prepopulated with the given [CalcEntity], if provided */
  val editEntity: LiveData<CalcEntity?> get() = mutableEditEntity

  private val entitiesSubject = BehaviorSubject.createDefault<List<CalcEntity>>(emptyList())

  /**
   * Called when the view is created.
   * Reads saved state from SharedPreferences
   * and begins observing entities for list.
   */
  fun onCreate() {
    SavedStateManager
        .readFromSharedPreferences()
        ?.entities
        ?.let(entitiesSubject::onNext)

    entitiesSubject
        .subscribe { entities ->
          mutableEntityModels.postValue(composeListWithHeaders(entities))
          mutableCanCalculate.postValue(
            entities.any { it.type == CHARACTER } &&
            entities.any { it.type == MONSTER }
          )
        }
        .let(disposables::add)
  }

  /**
   * Called on stop; saves state to SharedPreferences.
   */
  fun onStop() {
    val list = CalcEntityList(
        date = Date(),
        entities = mutableEntityModels.value
            ?.filterIsInstance<EntityItem>()
            ?.map(EntityItem::entity)
            ?: emptyList()
    )

    SavedStateManager.saveToSharedPreferences(list)
  }

  override fun onEntityItemClicked(entity: CalcEntity) {
    mutableEditEntity.postValue(entity)
  }

  /** Called when the add button is clicked. */
  fun onAddEntityClicked() {
    mutableEditEntity.postValue(null)
  }

  /** Called when an entity is added or edited via the dialog. */
  fun onAddOrEditEntity(entity: CalcEntity) {
    val newList = (entitiesSubject.value ?: emptyList())
      .filter { it.id != entity.id }
    entitiesSubject.onNext(newList + entity)
  }

  /** Called when an entity is removed via the dialog. */
  fun onRemoveEntity(id: String) {
    val newList = entitiesSubject.value?.toMutableList() ?: mutableListOf()
    newList.removeAll { it.id == id }
    entitiesSubject.onNext(newList)
  }

  /**
   * Called when calculate is clicked.
   * Calculates the result and navigates to the Result Fragment.
   */
  fun onCalculateClicked() {
    val result = try {
      XPCalculator.calculate(entitiesSubject.value)
    } catch (e: CalculationException) {
      mutableMessages.postValue(resources.getString(e.userMessage))
      null
    }

    if (result != null) {
      mutableCalculate.postValue(DiscreteEvent(result))
    }
  }

  /**
   * Converts a list of [CalcEntity] objects to [CalculatorEpoxyModel]s and adds headers.
   */
  private fun composeListWithHeaders(entities: List<CalcEntity>): List<CalculatorEpoxyModel> {
    val models = mutableListOf<CalculatorEpoxyModel>()
    entities.sortedWith(entityComparator).mapTo(models, CalculatorEpoxyModel::EntityItem)
    EntityType.values().forEach { type ->
      models.indexOfFirst { it is EntityItem && it.entity.type == type }
          .takeIf { it > -1 }
          ?.let { models.add(it, HeaderItem(type.header)) }
    }
    return models
  }
}
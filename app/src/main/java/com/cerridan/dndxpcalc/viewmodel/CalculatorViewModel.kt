package com.cerridan.dndxpcalc.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cerridan.dndxpcalc.adapter.calc.CalculatorCallbacks
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel.EntityItem
import com.cerridan.dndxpcalc.adapter.calc.CalculatorEpoxyModel.HeaderItem
import com.cerridan.dndxpcalc.model.CalcEntity
import com.cerridan.dndxpcalc.model.CalcEntityList
import com.cerridan.dndxpcalc.model.EntityType
import com.cerridan.dndxpcalc.model.EntityType.CHARACTER
import com.cerridan.dndxpcalc.model.EntityType.MONSTER
import com.cerridan.dndxpcalc.model.util.EntityFileWriter
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Date

class CalculatorViewModel(
  application: Application
) : BaseViewModel(application), CalculatorCallbacks {
  companion object {
    private val entityComparator = compareBy(
        { it.type.ordinal },
        { it.valueType.ordinal },
        CalcEntity::value,
        CalcEntity::quantity,
        CalcEntity::id
    )
  }

  private val mutableCanCalculate = MutableLiveData<Boolean>()
  val canCalculate: LiveData<Boolean> get() = mutableCanCalculate

  private val mutableEntityModels = MutableLiveData<List<CalculatorEpoxyModel>>()
  val entityModels: LiveData<List<CalculatorEpoxyModel>> get() = mutableEntityModels

  private val mutableEditEntity = MutableLiveData<CalcEntity?>()
  val editEntity: LiveData<CalcEntity?> get() = mutableEditEntity

  private val entitiesSubject = BehaviorSubject.createDefault<List<CalcEntity>>(emptyList())

  fun onCreate() {
    EntityFileWriter
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

  fun onDestroy() {
    val list = CalcEntityList(
        date = Date(),
        entities = mutableEntityModels.value
            ?.filterIsInstance<EntityItem>()
            ?.map(EntityItem::entity)
            ?: emptyList()
    )

    EntityFileWriter.saveToSharedPreferences(list)
  }

  override fun onEntityItemClicked(entity: CalcEntity) {
    mutableEditEntity.postValue(entity)
  }

  fun onAddEntityClicked() {
    mutableEditEntity.postValue(null)
  }

  fun onAddOrEditEntity(entity: CalcEntity) {
    val newList = (entitiesSubject.value ?: emptyList())
      .filter { it.id != entity.id }
    entitiesSubject.onNext(newList + entity)
  }

  fun onRemoveEntity(id: String) {
    val newList = entitiesSubject.value?.toMutableList() ?: mutableListOf()
    newList.removeAll { it.id == id }
    entitiesSubject.onNext(newList)
  }

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
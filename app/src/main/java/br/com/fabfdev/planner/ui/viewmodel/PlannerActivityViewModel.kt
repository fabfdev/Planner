package br.com.fabfdev.planner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fabfdev.planner.core.di.MainServiceLocator
import br.com.fabfdev.planner.core.di.MainServiceLocator.ioDispatcher
import br.com.fabfdev.planner.data.datasource.PlannerActivityLocalDataSource
import br.com.fabfdev.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.UUID

class PlannerActivityViewModel : ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    init {
        viewModelScope.launch {
            plannerActivityLocalDataSource.plannerActivities
                .flowOn(ioDispatcher)
                .collect { activities ->
                    _activities.emit(activities)
                }
        }
    }

    fun insertPlannerActivity(name: String, datetime: Long) {
        viewModelScope.launch(ioDispatcher) {
            val plannerActivity = PlannerActivity(
                uuid = UUID.randomUUID().toString(),
                name = name,
                datetime = datetime,
                isCompleted = false
            )
            plannerActivityLocalDataSource.insert(plannerActivity)
        }
    }

    fun update(updatedPlannerActivity: PlannerActivity) {
        viewModelScope.launch(ioDispatcher) {
            plannerActivityLocalDataSource.update(
                updatedPlannerActivity
            )
        }
    }

    fun updateIsCompleted(uuid: String, isCompleted: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            plannerActivityLocalDataSource.updateIsCompletedByUUID(
                uuid,
                isCompleted
            )
        }
    }

    fun delete(uuid: String) {
        viewModelScope.launch(ioDispatcher) { plannerActivityLocalDataSource.deleteByUUID(uuid) }
    }

}
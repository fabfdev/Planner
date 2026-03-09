package br.com.fabfdev.planner.presentation.ui.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fabfdev.planner.core.di.MainServiceLocator
import br.com.fabfdev.planner.core.di.MainServiceLocator.ioDispatcher
import br.com.fabfdev.planner.data.datasource.PlannerActivityLocalDataSource
import br.com.fabfdev.planner.domain.model.PlannerActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class PlannerActivityViewModel : ViewModel() {

    val plannerActivityLocalDataSource: PlannerActivityLocalDataSource by lazy {
        MainServiceLocator.plannerActivityLocalDataSource
    }

    private val _activities: MutableStateFlow<List<PlannerActivity>> = MutableStateFlow(emptyList())
    val activities: StateFlow<List<PlannerActivity>> = _activities.asStateFlow()

    private val newActivity: MutableStateFlow<NewPlannerActivity> = MutableStateFlow(
        NewPlannerActivity()
    )

    fun updateNewActivity(
        name: String? = null,
        date: SetDate? = null,
        time: SetTime? = null,
    ) {
        if (name == null && date == null && time == null) {
            return
        }

        newActivity.update { current ->
            current.copy(
                name = name ?: current.name,
                date = date ?: current.date,
                time = time ?: current.time
            )
        }
    }

    fun saveNewActivity(onSuccess: () -> Unit, onError: () -> Unit) {
        newActivity.value.let { newActivity ->
            if (newActivity.isFilled()) {
                insert(
                    name = newActivity.name.orEmpty(),
                    datetime = createNewPlannerActivityFilledCalendar().timeInMillis,
                )
                this@PlannerActivityViewModel.newActivity.update { NewPlannerActivity() }
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun fetchActivities() {
        viewModelScope.launch {
            plannerActivityLocalDataSource.plannerActivities
                .flowOn(ioDispatcher)
                .collect { activities ->
                    _activities.emit(activities)
                }
        }
    }

    fun insert(name: String, datetime: Long) {
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

    private fun createNewPlannerActivityFilledCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        return calendar.apply {
            newActivity.value.let { newActivity ->
                set(Calendar.YEAR, newActivity.date?.year ?: 0)
                set(Calendar.MONTH, newActivity.date?.month ?: 0)
                set(Calendar.DAY_OF_MONTH, newActivity.date?.dayOfMonth ?: 0)
                set(Calendar.HOUR_OF_DAY, newActivity.time?.hourOfDay ?: 0)
                set(Calendar.MINUTE, newActivity.time?.minute ?: 0)
            }
        }
    }

}
package br.com.fabfdev.planner.data.datasource

import br.com.fabfdev.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.Flow

interface PlannerActivityLocalDataSource {
    val plannerActivities: Flow<List<PlannerActivity>>
    fun insert(plannerActivity: PlannerActivity)
    fun getByUUID(uuid: String): PlannerActivity
    fun updateIsCompletedByUUID(uuid: String, isCompleted: Boolean)
    fun update(plannerActivity: PlannerActivity)
    fun deleteByUUID(uuid: String)
}
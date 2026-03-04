package br.com.fabfdev.planner.data.datasource

import br.com.fabfdev.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.Flow

interface PlannerActivityLocalDataSource {
    val plannerActivities: Flow<List<PlannerActivity>>
    suspend fun insert(plannerActivity: PlannerActivity)
    suspend fun getByUUID(uuid: String): PlannerActivity
    suspend fun updateIsCompletedByUUID(uuid: String, isCompleted: Boolean)
    suspend fun update(plannerActivity: PlannerActivity)
    suspend fun deleteByUUID(uuid: String)
}
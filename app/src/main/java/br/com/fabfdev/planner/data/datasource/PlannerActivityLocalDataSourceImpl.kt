package br.com.fabfdev.planner.data.datasource

import br.com.fabfdev.planner.data.database.PlannerActivityDao
import br.com.fabfdev.planner.domain.mapper.toDomain
import br.com.fabfdev.planner.domain.mapper.toEntity
import br.com.fabfdev.planner.domain.model.PlannerActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlannerActivityLocalDataSourceImpl(
    private val plannerActivityDao: PlannerActivityDao,
) : PlannerActivityLocalDataSource {
    override val plannerActivities: Flow<List<PlannerActivity>>
        get() = plannerActivityDao.getAll().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }

    override fun insert(plannerActivity: PlannerActivity) {
        plannerActivityDao.insert(plannerActivity.toEntity(0))
    }

    override fun getByUUID(uuid: String): PlannerActivity {
        return plannerActivityDao.getByUUID(uuid).toDomain()
    }

    override fun updateIsCompletedByUUID(uuid: String, isCompleted: Boolean) {
        plannerActivityDao.updateIsCompletedByUUID(uuid, isCompleted)
    }

    override fun update(plannerActivity: PlannerActivity) {
        val entity = plannerActivityDao.getByUUID(uuid = plannerActivity.uuid)
        plannerActivityDao.update(plannerActivityEntity = plannerActivity.toEntity(id = entity.id))
    }

    override fun deleteByUUID(uuid: String) {
        plannerActivityDao.deleteByUUID(uuid)
    }
}
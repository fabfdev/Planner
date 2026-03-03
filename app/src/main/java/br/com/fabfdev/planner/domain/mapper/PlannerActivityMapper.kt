package br.com.fabfdev.planner.domain.mapper

import br.com.fabfdev.planner.data.database.PlannerActivityEntity
import br.com.fabfdev.planner.domain.model.PlannerActivity

fun PlannerActivityEntity.toDomain(): PlannerActivity =
    PlannerActivity(
        uuid = this.uuid,
        name = this.name,
        datetime = this.datetime,
        isCompleted = this.isCompleted
    )

fun PlannerActivity.toEntity(id: Int): PlannerActivityEntity =
    PlannerActivityEntity(
        id = id,
        uuid = this.uuid,
        name = this.name,
        datetime = this.datetime,
        isCompleted = this.isCompleted
    )
package br.com.fabfdev.planner.domain.model

import br.com.fabfdev.planner.domain.utils.createCalendarFromTimeInMillis
import br.com.fabfdev.planner.domain.utils.toPlannerActivityDate
import br.com.fabfdev.planner.domain.utils.toPlannerActivityDateTime
import br.com.fabfdev.planner.domain.utils.toPlannerActivityTime

data class PlannerActivity(
    val uuid: String,
    val name: String,
    val datetime: Long,
    val isCompleted: Boolean,
) {
    private val datetimeCalendar = createCalendarFromTimeInMillis(datetime)
    val dateString = datetimeCalendar.toPlannerActivityDate()
    val timeString = datetimeCalendar.toPlannerActivityTime()
    val datetimeString = datetimeCalendar.toPlannerActivityDateTime()
}
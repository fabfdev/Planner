package br.com.fabfdev.planner.domain.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

private val sdfPlannerActivityDateTime = SimpleDateFormat(
    "EEE dd' \n'HH:mm",
    Locale.forLanguageTag("pt-BR")
)
private val sdfPlannerActivityDate = SimpleDateFormat(
    "dd 'de' MMMM",
    Locale.forLanguageTag("pt-BR")
)
private val sdfPlannerActivityTime = SimpleDateFormat(
    "HH:mm",
    Locale.forLanguageTag("pt-BR")
)

fun createCalendarFromTimeInMillis(timeInMillis: Long): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    return calendar
}

fun Calendar.toPlannerActivityDateTime(): String =
    sdfPlannerActivityDateTime.format(this)

fun Calendar.toPlannerActivityDate(): String =
    sdfPlannerActivityDate.format(this)

fun Calendar.toPlannerActivityTime(): String =
    sdfPlannerActivityTime.format(this)
package com.vanpra.composematerialdialogs.datetime.util

import androidx.compose.ui.geometry.Offset
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.Month
import java.time.Year
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

internal fun Float.getOffset(angle: Double): Offset =
    Offset((this * cos(angle)).toFloat(), (this * sin(angle)).toFloat())

internal val LocalTime.isAM: Boolean
    get() = this.hour in 0..11

internal val LocalTime.simpleHour: Int
    get() {
        val tempHour = this.hour % 12
        return if (tempHour == 0) 12 else tempHour
    }

internal fun Month.getShortLocalName(locale: Locale): String =
    this.getDisplayName(java.time.format.TextStyle.SHORT, locale)

internal fun Month.getFullLocalName(locale: Locale) =
    this.getDisplayName(java.time.format.TextStyle.FULL, locale)

internal fun DayOfWeek.getShortLocalName(locale: Locale) =
    this.getDisplayName(java.time.format.TextStyle.SHORT, locale)

internal fun LocalTime.toAM(): LocalTime = if (this.isAM) this else this.minusHours(12)
internal fun LocalTime.toPM(): LocalTime = if (!this.isAM) this else this.plusHours(12)

internal fun LocalTime.noSeconds(): LocalTime = LocalTime.of(this.hour, this.minute)

internal fun LocalDate.isLeapYear() = Year.isLeap(year.toLong())

internal fun LocalDate.withDayOfMonth(dayOfMonth: Int) =
    LocalDate(year = year, month = month, dayOfMonth = dayOfMonth)

internal fun LocalDate.Companion.now(): LocalDate =
    Clock.System.now().toLocalDateTime(
        TimeZone.currentSystemDefault()
    ).date

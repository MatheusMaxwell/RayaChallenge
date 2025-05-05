package com.raya.challenge.common.util

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime

object DateUtil {

    @OptIn(ExperimentalTime::class)
    fun getFormattedDateTime(date: String): String {
        val instant: Instant = Instant.parse(date)
        val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val month = localDateTime.monthNumber.toString().padStart(2, '0')
        val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
        val year = localDateTime.year.toString()
        val hour = localDateTime.hour.toString().padStart(2, '0')
        val minute = localDateTime.minute.toString().padStart(2, '0')

        return "$month/$day/$year $hour:$minute"
    }
}
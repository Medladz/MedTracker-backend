package com.medtracker.services.dto

import com.medtracker.repositories.dao.WeightOrVolume
import org.joda.time.DateTime


data class AgendaRDTO(
    val creatorID: Int,
    val drugID: Int,
    val containerID: Int,
    val title: String,
    val note: String,
    val quantity: Int,
    val measurementUnit: WeightOrVolume,
    val consumedAt: DateTime
)

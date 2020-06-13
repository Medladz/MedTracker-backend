package com.medtracker.services.dto

import com.medtracker.repositories.enumTypes.MeasurementUnit

//Format Data Transfer Object
data class AgendaFDTO(
    val title: String,
    val note: String? = null,
    val quantity: Int,
    val measurementUnit: MeasurementUnit,
    val consumedAt: String,
    val creatorID: Int,
    val drugID: Int,
    val containerID: Int? = null
)

//Response Data Transfer Object

data class AgendaRDTO(
    val creator: Int,
    val drugID: Int,
    val containerID: Int,
    val title: String,
    val note: String,
    val quantity: Int,
    val measurementUnit: MeasurementUnit,
    val consumedAt: String
)
package com.medtracker.models

// @todo dit moet naar dto map en RDTO en FDTO van maken
data class DrugComponentDTO(
    val componentID: Int,
    val purity: Double,
    val quantity: Int,
    val measurementUnit: String
)
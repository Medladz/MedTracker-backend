package com.medtracker.models


data class DrugComponent(
    var drugID: Int,
    var componentID: Int,
    var purity: Double,
    var quantity: Int,
    var measurementUnit: String

)

data class DrugComponentDTO(
    val componentID: Int,
    val purity: Double,
    val quantity: Int,
    val measurementUnit: String
)
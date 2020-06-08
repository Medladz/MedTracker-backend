package com.medtracker.models

class Drug(
    var id: Int? = null,
    var name: String? = null,
    var thumbnailURL: String? = null,
    var purity: Double? = null,
    var quantity: Int? = null,
    var measurementUnit: String? = null,
    var brand: Brand? = null,
    var source: Source? = null,
    var components: ArrayList<Drug>? = null,
    var creator: User? = null
)

// @todo dit moet naar dto map en RDTO en FDTO van maken
data class DrugDTO(
    val creatorID: Int,
    val brandID: Int,
    val sourceID: Int,
    val name: String,
    val thumbnailURL: String?
)
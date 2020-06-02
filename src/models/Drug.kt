package com.medtracker.models


data class Drug(
    var id: Int,
    var creatorID: Int,
    var brandID: Int,
    var sourceID: Int,
    var name: String,
    var thumbnailURL: String


)

data class DrugDTO(
    val creatorID: Int,
    val brandID: Int,
    val sourceID: Int,
    val name: String,
    val thumbnailURL: String
)
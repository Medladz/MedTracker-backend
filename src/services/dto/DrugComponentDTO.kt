package com.medtracker.services.dto

data class DrugComponentRelationshipsRDTO(
    val drug: RelationRDTO,
    val componentOf: RelationRDTO
)

data class DrugComponentRDTO(
    val quantity: String?,
    val purity: String?
)
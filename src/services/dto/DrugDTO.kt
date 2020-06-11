package com.medtracker.services.dto

data class DrugRelationshipsRDTO(
    val brand: RelationRDTO? = null,
    val source: RelationRDTO? = null,
    val components: List<RelationRDTO> = listOf()
)

data class DrugRDTO(
    val name: String?,
    val thumbnailURL: String?
)
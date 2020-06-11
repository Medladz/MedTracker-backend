package com.medtracker.services.dto

data class ResourcesResponseRDTO<resource, relationshipsRDTO>(
    var data: List<ResourceRDTO<resource, relationshipsRDTO>>
)

data class ResourceRDTO<resourceRDTO, relationshipsRDTO>(
    val type: String,
    val id: String,
    val attributes: resourceRDTO,
    val relationships: relationshipsRDTO?,
    val included: HashMap<String, Any?>? = null
)

data class RelationRDTO(
    val type: String,
    val id: String
)
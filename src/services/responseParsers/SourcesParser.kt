package com.medtracker.services.responseParsers

import com.medtracker.models.Source
import com.medtracker.services.dto.ResourceRDTO
import com.medtracker.services.dto.SourceRDTO

class SourcesParser {
    fun parseSingle(source: Source): ResourceRDTO<SourceRDTO, Unit> {
        return ResourceRDTO(
            type = "sources",
            id = source.id.toString(),
            attributes = SourceRDTO(
                name = source.name.toString()
            ),
            relationships = null,
            included = null
        )
    }
}
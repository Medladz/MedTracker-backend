package com.medtracker.services.RDTOParsers

import com.medtracker.models.Brand
import com.medtracker.models.Source
import com.medtracker.services.dto.*

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
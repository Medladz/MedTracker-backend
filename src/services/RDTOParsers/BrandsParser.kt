package com.medtracker.services.RDTOParsers

import com.medtracker.models.Brand
import com.medtracker.services.dto.*

class BrandsParser {
    fun parseSingle(brand: Brand): ResourceRDTO<BrandRDTO, Unit> {
        return ResourceRDTO(
            type = "brands",
            id = brand.id.toString(),
            attributes = BrandRDTO(
                name = brand.name.toString()
            ),
            relationships = null,
            included = null
        )
    }
}
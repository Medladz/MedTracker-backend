package com.medtracker.services.responseParsers

import com.medtracker.models.Brand
import com.medtracker.services.dto.BrandRDTO
import com.medtracker.services.dto.ResourceRDTO

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
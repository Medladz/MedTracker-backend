package com.medtracker.services.RDTOParsers

import com.medtracker.models.Drug
import com.medtracker.services.dto.*

class DrugComponentsParser {

    fun parseSingle(drugComponent: Drug): ResourceRDTO<DrugComponentRDTO, Unit> {
        return ResourceRDTO(
            type = "drugs",
            id = drugComponent.id.toString(),
            attributes = DrugComponentRDTO(
                name = drugComponent.name,
                thumbnailURL = drugComponent.thumbnailURL,
                quantity = drugComponent.getQuantityWithUnit(),
                purity = drugComponent.getPurityPercentage()
            ),
            relationships = null,
            included = null
        )
    }

    fun parseMultiple(drugComponents: ArrayList<Drug>): List<ResourceRDTO<DrugComponentRDTO, Unit>> {
        return drugComponents.map { parseSingle(it) }
    }
}
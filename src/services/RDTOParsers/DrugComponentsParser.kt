package com.medtracker.services.RDTOParsers

import com.medtracker.models.Drug
import com.medtracker.services.dto.*

class DrugComponentsParser {

    fun parseSingle(drugComponent: Drug): ResourceRDTO<DrugComponentRDTO, DrugComponentRelationshipsRDTO> {
        return ResourceRDTO(
            type = "drugs",
            id = drugComponent.id.toString(),
            attributes = DrugComponentRDTO(
                quantity = drugComponent.getQuantityWithUnit(),
                purity = drugComponent.getPurityPercentage()
            ),
            relationships = DrugComponentRelationshipsRDTO(
                drug = RelationRDTO(
                    type = "drugs",
                    id = drugComponent.id.toString()
                ),
                componentOf = RelationRDTO(
                    type = "drugs",
                    id = drugComponent.componentOf?.id.toString()
                )
            ),
            included = null
        )
    }

    fun parseMultiple(drugComponents: ArrayList<Drug>): List<ResourceRDTO<DrugComponentRDTO, DrugComponentRelationshipsRDTO>> {
        return drugComponents.map { parseSingle(it) }
    }
}
package com.medtracker.services.FDTOParsers

import com.medtracker.models.Drug
import com.medtracker.services.RDTOParsers.BrandsParser
import com.medtracker.services.RDTOParsers.ContainersParser
import com.medtracker.services.RDTOParsers.DrugComponentsParser
import com.medtracker.services.RDTOParsers.SourcesParser
import com.medtracker.services.dto.*

class DrugsParser(val includedResources: List<String>?) {

    fun parseSingle(drug: Drug): ResourceRDTO<DrugRDTO, DrugRelationshipsRDTO> {
        return ResourceRDTO(
            type = "drugs",
            id = drug.id.toString(),
            attributes = DrugRDTO(
                name = drug.name,
                thumbnailURL = drug.thumbnailURL
            ),
            relationships = DrugRelationshipsRDTO(
                brand = drug.brand?.let {
                    RelationRDTO(
                        type = "brands",
                        id = it.id.toString()
                    )
                },
                source = drug.source?.let {
                    RelationRDTO(
                        type = "sources",
                        id = it.id.toString()
                    )
                },
                containers = drug.containers.map {
                    RelationRDTO(
                        type = "containers",
                        id = it.id.toString()
                    )
                },
                components = drug.components.map {
                    RelationRDTO(
                        type = "drugs",
                        id = it.id.toString()
                    )
                }
            ),
            included = includedResources?.let {
                val included = hashMapOf<String, Any?>()

                if (it.contains("brands"))
                    included["brands"] = drug.brand?.let { BrandsParser().parseSingle(drug.brand!!) }

                if (it.contains("sources"))
                    included["sources"] = drug.source?.let { SourcesParser().parseSingle(drug.source!!) }

                // @todo dit uitbreiden
                if (it.contains("components"))
                    included["components"] = DrugComponentsParser().parseMultiple(drug.components)

                if (it.contains("containers"))
                    included["containers"] = ContainersParser().parseMultiple(drug.containers)

                included
            }
        )
    }

    fun parseMultiple(drugs: ArrayList<Drug>): List<ResourceRDTO<DrugRDTO, DrugRelationshipsRDTO>> {
        return drugs.map { parseSingle(it) }
    }

    fun parse(drugs: ArrayList<Drug>): ResourcesResponseRDTO<DrugRDTO, DrugRelationshipsRDTO> {
        return ResourcesResponseRDTO(data = parseMultiple(drugs))
    }
}
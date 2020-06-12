package com.medtracker.services.RDTOParsers

import com.medtracker.models.Container
import com.medtracker.services.dto.*

class ContainersParser {

    fun parseSingle(container: Container): ResourceRDTO<ContainerRDTO, Unit>{
        return ResourceRDTO(
            type = "containers",
            id = container.id.toString(),
            attributes = ContainerRDTO(
                name = container.name,
                quantity = container.getQuantityWithUnit(),
                thumbnailURL = container.thumbnailURL
            ),
            relationships = null,
            included = null
        )
    }

    fun parseMultiple(containers: ArrayList<Container>): List<ResourceRDTO<ContainerRDTO, Unit>> {
        return containers.map { parseSingle(it) }
    }
}
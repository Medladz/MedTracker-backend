package com.medtracker.services.responseParsers

import com.medtracker.models.Container
import com.medtracker.services.dto.ContainerRDTO
import com.medtracker.services.dto.ResourceRDTO

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
package com.medtracker.services.FDTOParsers

import com.medtracker.models.Drug
import com.medtracker.services.dto.DrugRDTO

class DrugParser(private val drugDTO: DrugRDTO) {

    fun parse(drug: Drug){
        drugDTO.id = drug.id

//        agenda.creator = User(id = agendaFDTO.creatorID)
//        agenda.drug = Drug(id = agendaFDTO.drugID)
//        agenda.container = Container(id = agendaFDTO.containerID)
//        agenda.title =  agendaFDTO.title
//        agenda.note = agendaFDTO.note
//        agenda.quantity = agendaFDTO.quantity
//        agenda.measurementUnit = agendaFDTO.measurementUnit
//        agenda.consumedAt = DateTime.parse(agendaFDTO.consumedAt)
    }
}
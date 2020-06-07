package com.medtracker.services.FDTOParsers

import com.medtracker.models.Agenda
import com.medtracker.models.Container
import com.medtracker.models.Drug
import com.medtracker.models.User
import com.medtracker.services.dto.AgendaFDTO
import org.joda.time.DateTime

class AgendaParser(val agenda : Agenda) {

    fun parse(agendaFDTO: AgendaFDTO){
        agenda.creator = User(id = agendaFDTO.creatorID)
        agenda.drug = Drug(id = agendaFDTO.drugID)
        agenda.container = Container(id = agendaFDTO.containerID)
        agenda.title =  agendaFDTO.title
        agenda.note = agendaFDTO.note
        agenda.quantity = agendaFDTO.quantity
        agenda.measurementUnit = agendaFDTO.measurementUnit
        agenda.consumedAt = DateTime.parse(agendaFDTO.consumedAt)

    }
}
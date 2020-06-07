package com.medtracker.services

import com.medtracker.models.Agenda
import com.medtracker.repositories.AgendaRepository
import com.medtracker.services.FDTOParsers.AgendaParser
import com.medtracker.services.dto.AgendaFDTO

class AgendaService {

    fun createAgendaEntry(agendaFDTO: AgendaFDTO) {
        val agendaRepository = AgendaRepository()
        val agendaParser = AgendaParser(Agenda())
        agendaParser.parse(agendaFDTO)
        return agendaRepository.createAgendaEntry(agendaParser.agenda)
    }

}
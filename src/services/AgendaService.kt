package com.medtracker.services

import com.medtracker.models.Agenda
import com.medtracker.repositories.AgendaRepository
import com.medtracker.services.dto.AgendaFDTO
import com.medtracker.services.responseParsers.AgendaParser

class AgendaService {

    fun createAgendaEntry(agendaFDTO: AgendaFDTO,creatorId: Int) {
        val agendaRepository = AgendaRepository()
        val agendaParser = AgendaParser(Agenda())
        agendaParser.parse(agendaFDTO)
        return agendaRepository.createAgendaEntry(agendaParser.agenda,creatorId)
    }

    fun getAgendaEntriesByCreator(creatorId: Int, includedResources: List<String>?): ArrayList<Agenda> {
        val agendaRepository = AgendaRepository()
        return agendaRepository.getAgendaEntriesByCreator(creatorId, includedResources)
    }

    fun updateAgendaEntry(agendaId: Int, agenda: Agenda){
        val agendaRepository = AgendaRepository()
        val agendaEntry = agendaRepository.updateAgendaEntry(agendaId, agenda)
        return agendaEntry
    }
    fun deleteAgendaEntry(agendaId: Int){
        val agendaRepository = AgendaRepository()
        agendaRepository.deleteAgendaEntry(agendaId)
    }
}
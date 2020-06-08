package com.medtracker.controllers

import com.medtracker.models.Agenda
import com.medtracker.services.AgendaService
import com.medtracker.services.dto.AgendaFDTO

class AgendaController {

    fun createAgendaEntry(agenda: AgendaFDTO) {
        val agendaService = AgendaService()
        val agendaEntry = agendaService.createAgendaEntry(agenda)
        return agendaEntry
    }
    fun getAgendaEntriesByCreator(creatorId: Int, includedResources: List<String>?): ArrayList<Agenda>{
        val agendaService = AgendaService()
        val agendaEntries = agendaService.getAgendaEntriesByCreator(creatorId, includedResources)
        return agendaEntries
    }

}
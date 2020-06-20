package com.medtracker.controllers

import com.medtracker.models.Agenda
import com.medtracker.services.AgendaService
import com.medtracker.services.JWTAuth
import com.medtracker.services.UserService
import com.medtracker.services.dto.AgendaFDTO
import com.medtracker.services.responseParsers.AuthParser
import com.medtracker.utilities.UnprocessableEntityException
import java.lang.IllegalArgumentException

class AgendaController {

    fun createAgendaEntry(agenda: AgendaFDTO,creatorId: Int) {
        val agendaService = AgendaService()
        val agendaEntry = agendaService.createAgendaEntry(agenda,creatorId)
        return agendaEntry
    }
    fun getAgendaEntriesByCreator(creatorId: Int, includedResources: List<String>?): ArrayList<Agenda>{
        val agendaService = AgendaService()
        val agendaEntries = agendaService.getAgendaEntriesByCreator(creatorId, includedResources)
        return agendaEntries
    }
    fun updateAgendaEntry(agendaId: Int, agenda: Agenda){
        val agendaService = AgendaService()
        val agendaEntries = agendaService.updateAgendaEntry(agendaId, agenda)
        return agendaEntries
    }
    fun deleteAgendaEntry(agendaId: Int){
        val agendaService = AgendaService()
        agendaService.deleteAgendaEntry(agendaId)
    }
}
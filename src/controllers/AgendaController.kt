package com.medtracker.controllers

import com.medtracker.services.AgendaService
import com.medtracker.services.dto.AgendaFDTO

class AgendaController {

    fun createAgendaEntry(agenda: AgendaFDTO) {
        val agendaService = AgendaService()


        val agendaEntry = agendaService.createAgendaEntry(agenda)

        return agendaEntry
    }

}
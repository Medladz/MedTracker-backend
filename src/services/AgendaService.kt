package com.medtracker.services

import com.medtracker.models.Drug
import com.medtracker.repositories.AgendaRepository
import com.medtracker.repositories.DrugRepository
import com.medtracker.services.dto.AgendaRDTO

class AgendaService {

    fun createAgendaEntry(agenda: AgendaRDTO) {
        val agendaRepository = AgendaRepository()

        return agendaRepository.createAgendaEntry(agenda)
    }

}
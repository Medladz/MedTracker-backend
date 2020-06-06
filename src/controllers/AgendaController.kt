package com.medtracker.controllers

import com.medtracker.models.Drug
import com.medtracker.models.DrugDTO
import com.medtracker.services.AgendaService
import com.medtracker.services.DrugService
import com.medtracker.services.UserService
import com.medtracker.services.dto.AgendaRDTO
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.joda.time.DateTime
import org.w3c.dom.Text

class AgendaController {

    fun createAgendaEntry(agenda: AgendaRDTO) {

        val agendaService = AgendaService()

        val agendaEntry = agendaService.createAgendaEntry(agenda)

        return agendaEntry
    }

}
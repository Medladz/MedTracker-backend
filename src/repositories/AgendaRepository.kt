package com.medtracker.repositories


import com.medtracker.repositories.dao.AgendaDAO

import com.medtracker.services.dto.AgendaRDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


class AgendaRepository {

    fun createAgendaEntry(agenda: AgendaRDTO) {
        transaction {
            AgendaDAO.insert {
                it[creatorID] = agenda.creatorID
                it[drugID] = agenda.drugID
                it[containerID] = agenda.containerID
                it[title] = agenda.title
                it[note] = agenda.note
                it[quantity] = agenda.quantity
                it[measurementUnit] = agenda.measurementUnit
                it[consumedAt] = agenda.consumedAt
            }
        }
    }
}


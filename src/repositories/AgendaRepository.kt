package com.medtracker.repositories


import com.medtracker.models.Agenda
import com.medtracker.repositories.dao.AgendaDAO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.postgresql.jdbc.PgResultSet.toInt


class AgendaRepository {

    fun createAgendaEntry(agenda: Agenda) {
        transaction {
            AgendaDAO.insert {
                it[creatorID] = toInt((agenda.creator?.id).toString())
                it[drugID]= toInt((agenda.drug?.id).toString())
                it[containerID] = agenda.container?.id
                it[title] = agenda.title
                it[note] = agenda.note
                it[quantity] = agenda.quantity
                it[measurementUnit] = agenda.measurementUnit
                it[consumedAt] = DateTime.parse(agenda.consumedAt.toString())
            }
        }
    }
}


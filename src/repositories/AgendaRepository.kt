package com.medtracker.repositories


import com.medtracker.models.Agenda
import com.medtracker.models.Container
import com.medtracker.models.Drug
import com.medtracker.repositories.dao.AgendaDAO
import com.medtracker.repositories.dao.AgendaDAO.containerID
import com.medtracker.repositories.dao.AgendaDAO.drugID
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
    fun getAgendaEntriesByCreator(creatorId: Int): ArrayList<Agenda>{
        var agendaEntries: ArrayList<Agenda> = arrayListOf()
        transaction {
            AgendaDAO.select {AgendaDAO.creatorID eq creatorId}.map {
                val agendaEntry = Agenda(
                    drug = Drug(id = it[drugID]),
                    container = Container(id = it[containerID]),
                    title = it[AgendaDAO.title],
                    note = it[AgendaDAO.note],
                    quantity = it[AgendaDAO.quantity],
                    measurementUnit = it[AgendaDAO.measurementUnit],
                    consumedAt = DateTime.parse(it[AgendaDAO.consumedAt].toString())
                )
                agendaEntries.add(agendaEntry)
            }
        }
        return agendaEntries
    }
}


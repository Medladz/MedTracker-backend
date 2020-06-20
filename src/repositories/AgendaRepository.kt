package com.medtracker.repositories


import com.medtracker.models.*
import com.medtracker.repositories.dao.*
import com.medtracker.repositories.dao.AgendaDAO.containerID
import com.medtracker.repositories.dao.AgendaDAO.drugID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.postgresql.jdbc.PgResultSet.toInt
import com.medtracker.repositories.enumTypes.MeasurementUnit


class AgendaRepository {

    fun createAgendaEntry(agenda: Agenda) {
        transaction {
            AgendaDAO.insert {
                it[creatorID] = toInt((agenda.creator?.id).toString())
                it[drugID]= toInt((agenda.drug?.id).toString())
                it[containerID] = agenda.container?.id
                it[title] = agenda.title ?: ""
                it[note] = agenda.note
                it[quantity] = agenda.quantity
                it[measurementUnit] = agenda.measurementUnit
                it[consumedAt] = DateTime.parse(agenda.consumedAt.toString())
            }
        }
    }
    fun getAgendaEntriesByCreator(creatorId: Int, includedResources: List<String>?): ArrayList<Agenda>{
        var agendaEntries: ArrayList<Agenda> = arrayListOf()
        transaction {

            val agendaRecords =  AgendaDAO.select {AgendaDAO.creatorID eq creatorId}

            includedResources?.let {
                if (includedResources.contains("drug")) {
                    withDrug(agendaRecords)
                }
                if (includedResources.contains("container")) {
                    withContainer(agendaRecords)
                }
            }
            agendaRecords.map { result ->
                val agendaEntry = Agenda(
                    id = result[AgendaDAO.id],
                    drug = Drug(id = result[drugID]),
                    container = Container(id = result[containerID]),
                    title = result[AgendaDAO.title],
                    note = result[AgendaDAO.note],
                    quantity = result[AgendaDAO.quantity],
                    measurementUnit = result[AgendaDAO.measurementUnit],
                    consumedAt = DateTime.parse(result[AgendaDAO.consumedAt].toString())
                )

                includedResources?.let {
                    if (includedResources.contains("drug")) {
                        agendaEntry.drug?.id = result[drugID]
                        agendaEntry.drug?.name = result[DrugDAO.name]
                        agendaEntry.drug?.thumbnailURL = result[DrugDAO.thumbnailURL]
                        agendaEntry.drug?.source = Source(id = result[DrugDAO.sourceId])
                        agendaEntry.drug?.brand = Brand(id = result[DrugDAO.brandId])
                    }

                    if (includedResources.contains("container")) {
                        agendaEntry.container?.id = result[containerID]
                        agendaEntry.container?.name = result[ContainerDAO.name]
                        agendaEntry.container?.quantity = result[ContainerDAO.quantity]
                        agendaEntry.container?.measurementUnit = result[ContainerDAO.measurementUnit]
                        agendaEntry.container?.thumbnailURL = result[ContainerDAO.thumbnailURL]
                    }
                }

                agendaEntries.add(agendaEntry)
            }
        }
        return agendaEntries
    }

    fun updateAgendaEntry(agendaId: Int, agendaEntry: Agenda){
        transaction {
            AgendaDAO.update({ AgendaDAO.id eq agendaId }) {
                it[creatorID] = toInt(agendaEntry.creator?.id.toString())
                it[drugID] = toInt(agendaEntry.drug?.id.toString())
                it[containerID] = agendaEntry.container?.id
                it[title] = agendaEntry.title ?: ""
                it[note] = agendaEntry.note
                it[quantity] = agendaEntry.quantity
                it[measurementUnit] = agendaEntry.measurementUnit
                it[consumedAt] = DateTime.parse(agendaEntry.consumedAt.toString())
            }
        }
    }
    fun deleteAgendaEntry(agendaId: Int){
        transaction {
            AgendaDAO.deleteWhere { AgendaDAO.id eq agendaId }
        }
    }

    // Get the Drug record of the Agenda
    private fun withDrug(query: Query) {
        query.adjustColumnSet { leftJoin(DrugDAO, { AgendaDAO.drugID }, { id }) }
            .adjustSlice { slice(fields + DrugDAO.columns) }
    }
    // Get the container record of the Agenda
    private fun withContainer(query: Query) {
        query.adjustColumnSet { leftJoin(ContainerDAO, { AgendaDAO.containerID }, { id }) }
            .adjustSlice { slice(fields + ContainerDAO.columns) }
    }
}


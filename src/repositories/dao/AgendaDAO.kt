package com.medtracker.repositories.dao

import com.medtracker.repositories.enumTypes.MeasurementUnit
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date
import org.joda.time.DateTime

object AgendaDAO : Table("\"agendaEntry\"") {
    val id: Column<Int> = integer("\"ID\"")
    val creatorID: Column<Int> = integer("creatorID").references(UserDAO.id)
    val drugID: Column<Int?> = integer("drugID").references(DrugDAO.id).nullable()
    val containerID: Column<Int?> = integer("containerID").references(ContainerDAO.id).nullable()
    val title: Column<String> = varchar("title",255)
    val note: Column<String?> = text("note").nullable()
    val quantity: Column<Int?> = integer("quantity").nullable()
    val measurementUnit: Column<MeasurementUnit?> = MeasurementUnit.pgColumn(this,"measurementUnit").nullable()
    val consumedAt: Column<DateTime> = date("consumedAt")

    override val primaryKey = PrimaryKey(id)
}
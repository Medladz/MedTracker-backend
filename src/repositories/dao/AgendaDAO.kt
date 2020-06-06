package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import kotlin.reflect.KClass
import org.postgresql.util.*

object AgendaDAO : Table("\"agendaEntry\"") {

    val id: Column<Int> = integer("\"ID\"").primaryKey()
    val creatorID: Column<Int?> = integer("creatorID").references(UserDAO.id).nullable()
    val drugID: Column<Int?> = integer("drugID").references(DrugDAO.id).nullable()
    val containerID: Column<Int?> = integer("containerID").references(DrugContainerDAO.containerId).nullable()
    val title: Column<String> = varchar("title",255)
    val note: Column<String> = text("note")
    val quantity: Column<Int> = integer("quantity")
    val measurementUnit= WeightOrVolume.pgColumn(this, "measurementUnit")
    val consumedAt: Column<DateTime> = date("consumedAt")
}

enum class WeightOrVolume {
    mg,
    ml;

    companion object {
        const val dbName = "medtracker"

        fun pgColumn(table: Table, name: String) = table.customEnumeration(
            name = name,
            sql = dbName,
            fromDb = { it.fromPg() },
            toDb = { it.toPg() }
        )

        private fun Any.fromPg() = valueOf(this as String)
        private fun WeightOrVolume?.toPg() = PgEnum(this)
    }

    class PgEnum(enumValue: WeightOrVolume?) : PGobject() {
        init {
            value = enumValue?.name
            type = "\"weightOrVolume\""
        }
    }
}
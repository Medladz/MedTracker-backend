package com.medtracker.repositories.enumTypes

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

enum class WeightOrVolume {
    mg,
    ml;

    companion object {
        fun pgColumn(table: Table, name: String) = table.customEnumeration(
            name = name,
            sql = "",
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
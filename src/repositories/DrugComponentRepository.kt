package com.medtracker.repositories

import org.jetbrains.exposed.sql.Table


object DrugComponentDAO : Table("\"drugComponent\"") {
    val drugID = integer("drugID").primaryKey()
    val componentID = integer("componentID")
    val purity = double("purity")
    val quantity = integer("quantity")
    val measurementUnit = text("measurementUnit")
}
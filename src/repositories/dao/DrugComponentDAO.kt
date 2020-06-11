package com.medtracker.repositories.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object DrugComponentDAO : Table("drugComponent"){
    val drugId: Column<Int> = integer("drugID").references(DrugDAO.id)
    val componentId: Column<Int> = integer("componentID").references(DrugDAO.id)
    val purity: Column<BigDecimal> = decimal("purity", 5, 2)
    val quantity: Column<Int> = integer("quantity")
    // @todo change to ENUM weightOrVolume
    val measurementUnit: Column<String> = text("measurementUnit")
    // @todo PrimaryKey
}
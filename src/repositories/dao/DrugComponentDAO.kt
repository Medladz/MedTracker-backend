package com.medtracker.repositories.dao

import com.medtracker.repositories.enumTypes.WeightOrVolume
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.math.BigDecimal

object DrugComponentDAO : Table("\"drugComponent\""){
    val drugId: Column<Int> = integer("drugID").references(DrugDAO.id)
    val componentId: Column<Int> = integer("componentID").references(DrugDAO.id)
    val purity: Column<BigDecimal?> = decimal("purity", 5, 2).nullable()
    val quantity: Column<Int?> = integer("quantity").nullable()
    val measurementUnit: Column<WeightOrVolume?> = WeightOrVolume.pgColumn(this,"measurementUnit").nullable()

    override val primaryKey = PrimaryKey(drugId, componentId)
}
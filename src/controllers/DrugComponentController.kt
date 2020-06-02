package com.medtracker.controllers

import com.medtracker.models.DrugComponent
import com.medtracker.models.DrugComponentDTO
import com.medtracker.repositories.DrugComponentDAO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DrugComponentController {

    fun getAll(drugID: Int): ArrayList<DrugComponent> {
        val drugComponents: ArrayList<DrugComponent> = arrayListOf()
        transaction {
            DrugComponentDAO.select{DrugComponentDAO.drugID eq drugID }.map {
                drugComponents.add(
                    DrugComponent(
                        drugID = drugID,
                        componentID = it[DrugComponentDAO.componentID],
                        purity = it[DrugComponentDAO.purity],
                        quantity = it[DrugComponentDAO.quantity],
                        measurementUnit = it[DrugComponentDAO.measurementUnit]
                    )
                )
            }
        }

        return drugComponents
    }
    fun insert(drugComponent: DrugComponent) {
        transaction {
            DrugComponentDAO.insert {
                it[componentID] = drugComponent.componentID
                it[purity] = drugComponent.purity
                it[quantity] = drugComponent.quantity
                it[measurementUnit] = drugComponent.measurementUnit
            }
        }
    }

    fun update(drugComponent: DrugComponentDTO, drugID: Int) {
        transaction {
            DrugComponentDAO.update({ DrugComponentDAO.drugID eq drugID }) {
                it[componentID] = drugComponent.componentID
                it[purity] = drugComponent.purity
                it[quantity] = drugComponent.quantity
                it[measurementUnit] = drugComponent.measurementUnit
            }
        }
    }

    fun delete(drugID: Int) {
        transaction {
            DrugComponentDAO.deleteWhere { DrugComponentDAO.drugID eq drugID }
        }
    }
}
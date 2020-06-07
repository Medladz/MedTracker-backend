package com.medtracker.controllers

import com.medtracker.models.Drug
import com.medtracker.models.DrugDTO
import com.medtracker.services.DrugService
import com.medtracker.services.UserService
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class DrugController {

    fun getAllByCreator(creatorId: Int, withVerified: Boolean, includedResources: List<String>? ): ArrayList<Drug> {
        val drugService = DrugService()

        val drugs = drugService.getAllByCreator(creatorId, withVerified, includedResources)

        // @todo service om model om te zetten naar RDTO om als response terug te sturen

        return drugs
    }

//    fun insert(drug: DrugDTO) {
//        val drugService = DrugService()
//
//        drugService
//    }

//    fun update(drug: DrugDTO, ID: Int) {
//        transaction {
//            DrugDAO.update({ DrugDAO.id eq ID }) {
//                it[creatorID] = drug.creatorID
//                it[brandID] = drug.brandID
//                it[sourceID] = drug.sourceID
//                it[name] = drug.name
//                it[thumbnailURL] = drug.thumbnailURL
//            }
//        }
//    }

//    fun delete(ID: Int) {
//        transaction {
//            DrugDAO.deleteWhere { DrugDAO.id eq ID }
//        }
//    }
}
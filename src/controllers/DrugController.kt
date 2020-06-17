package com.medtracker.controllers

import com.medtracker.models.Drug
import com.medtracker.services.DrugService
import com.medtracker.services.dto.DrugRDTO
import com.medtracker.services.dto.DrugRelationshipsRDTO
import com.medtracker.services.dto.ResourcesResponseRDTO
import com.medtracker.services.responseParsers.DrugsParser

class DrugController {

    /**
     * Request the [DrugService] to get a list of [Drug].
     * Parse this list into a [ResourcesResponseRDTO] and send it pack.
     */
    fun getAllByCreator(creatorId: Int, withVerified: Boolean, includedResources: List<String>? ): ResourcesResponseRDTO<DrugRDTO, DrugRelationshipsRDTO> {
        val drugService = DrugService()
        val drugsParser = DrugsParser(includedResources)

        val drugs: ArrayList<Drug> = drugService.getAllByCreator(creatorId, withVerified, includedResources)

       return drugsParser.parse(drugs)
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
package com.medtracker.controllers

import com.medtracker.models.Drug
import com.medtracker.services.DrugService
import com.medtracker.services.FDTOParsers.DrugsParser
import com.medtracker.services.dto.DrugsRDTO

class DrugController {

    fun getAllByCreator(creatorId: Int, withVerified: Boolean, includedResources: List<String>? ): ArrayList<Drug> {
        val drugService = DrugService()
        val drugsRDTO = DrugsRDTO()
        val drugsParser =  DrugsParser(drugsRDTO)

        val drugs = drugService.getAllByCreator(creatorId, withVerified, includedResources)

//        drugsParser.parse(drugs)
        return drugs
//        return drugsRDTO
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
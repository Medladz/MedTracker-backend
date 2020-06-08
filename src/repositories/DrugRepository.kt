package com.medtracker.repositories

import com.medtracker.models.Drug
import com.medtracker.models.Brand
import com.medtracker.models.Source
import com.medtracker.models.User
import com.medtracker.repositories.dao.BrandDAO
import com.medtracker.repositories.dao.DrugDAO
import com.medtracker.repositories.dao.SourceDAO
import com.medtracker.repositories.dao.UserDAO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @todo
 * fields fixen:
 * - probeer eerst alles te joinen dan pas slicen
 * - field is altijd alle fields van alle voorgaande aanvragingen
 * Innerjoin met users fixen, want creators en withVerified gaat niet samen
 */

class DrugRepository {

    fun getAllByCreator(creatorId: Int, withVerified: Boolean, includedResources: List<String>?): ArrayList<Drug> {
        val drugs: ArrayList<Drug> = arrayListOf()

        transaction {
            val drugRecords = DrugDAO.select { DrugDAO.creatorId eq creatorId }

            if (withVerified) {
                withVerified(drugRecords)
            }

            includedResources?.let {
                if (includedResources.contains("brands")) {
                    withBrand(drugRecords)
                }

                if (includedResources.contains("sources")) {
                    withSource(drugRecords)
                }

                if (includedResources.contains("creators")) {
                    withCreator(drugRecords)
                }
            }

            drugRecords.map { result ->
                val componentsOfDrug: ArrayList<Drug> = arrayListOf()

                val drugTable = DrugDAO.alias("drugTable")
                val componentOfDrugTable = DrugDAO.alias("componentOfDrugTable")

                val drug = Drug(
                    id = result[DrugDAO.id],
                    name = result[DrugDAO.name],
                    thumbnailURL = result[DrugDAO.thumbnailURL],
                    source = Source(id = result[DrugDAO.sourceId]),
                    brand = Brand(id = result[DrugDAO.brandId]),
                    creator = User(id = result[DrugDAO.creatorId])
                )

                val drugComponents = drugTable
                    .innerJoin(DrugComponentDAO, {drugTable[DrugDAO.id]}, {componentID})
                    .innerJoin(componentOfDrugTable, {DrugComponentDAO.drugID}, {componentOfDrugTable[DrugDAO.id]})
                    .select{DrugComponentDAO.componentID eq result[DrugDAO.id]}

                drugComponents.map {
                    val componentOfDrug = Drug(
                        id = it[componentOfDrugTable[DrugDAO.id]]
                    )

                    if (includedResources!!.contains("drugComponents")) {
                        componentOfDrug.name = it[componentOfDrugTable[DrugDAO.name]]
                        componentOfDrug.thumbnailURL = it[componentOfDrugTable[DrugDAO.thumbnailURL]]
                        componentOfDrug.purity = it[DrugComponentDAO.purity]
                        componentOfDrug.quantity = it[DrugComponentDAO.quantity]
                        componentOfDrug.measurementUnit = it[DrugComponentDAO.measurementUnit]
                    }

                    componentsOfDrug.add(componentOfDrug)
                }

                if(componentsOfDrug.isNotEmpty()) {
                    drug.components = componentsOfDrug
                }

                includedResources?.let {
                    if (includedResources.contains("brands")) {
                        drug.brand?.name = result[BrandDAO.name]
                    }

                    if (includedResources.contains("sources")) {
                        drug.source?.name = result[SourceDAO.name]
                    }

                    if (includedResources.contains("creators")) {
                        drug.creator?.username = result[UserDAO.username]
                        drug.creator?.email = result[UserDAO.email]
                        drug.creator?.password = result[UserDAO.password]
                        drug.creator?.verified = result[UserDAO.verified]
                        drug.creator?.birthday = result[UserDAO.birthday]
                    }
                }

                drugs.add(drug)
            }
        }

        return drugs
    }

    // Get drug records which are verified
    private fun withVerified(query: Query) {
        query.adjustColumnSet { innerJoin(UserDAO) }
            .orWhere { UserDAO.verified eq true }
    }

    // Get the brand record of the drug
    private fun withBrand(query: Query) {
        query.adjustColumnSet { leftJoin(BrandDAO, { DrugDAO.brandId }, { id }) }
            .adjustSlice { slice(fields - BrandDAO.columns) }
            .adjustSlice { slice(fields - BrandDAO.columns) }
    }

    // Get the source record of the drug
    private fun withSource(query: Query) {
        query.adjustColumnSet { leftJoin(SourceDAO, { DrugDAO.sourceId }, { id }) }
            .adjustSlice { slice(columns +  fields) }
    }

    // Get the creator record of the drug
    private fun withCreator(query: Query) {
        query.adjustColumnSet { leftJoin(UserDAO, { DrugDAO.creatorId }, { id }) }
            .adjustSlice { slice(fields + UserDAO.columns) }
    }
}


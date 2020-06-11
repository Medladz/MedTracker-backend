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

class DrugRepository(
    private val includedResources: List<String>? = null,
    private val withVerified: Boolean = true
) {
    fun getAllByCreator(creatorId: Int): ArrayList<Drug> {
        val drugs: ArrayList<Drug> = arrayListOf()

        transaction {
            val drugRecords = DrugDAO.select { DrugDAO.creatorId eq creatorId }

            withVerifiedDrugs(drugRecords)
            withIncludedResources(drugRecords)

            drugRecords.map { result ->
                val componentsOfDrug: ArrayList<Drug> = arrayListOf()
                val componentOfDrugTable = DrugDAO.alias("componentOfDrugTable")
                val fields = if (includedResources?.contains("drugComponents") == true) {
                    componentOfDrugTable.columns + DrugComponentDAO.columns
                } else {
                    listOf(componentOfDrugTable[DrugDAO.id])
                }

                val drug = Drug(
                    id = result[DrugDAO.id],
                    name = result[DrugDAO.name],
                    thumbnailURL = result[DrugDAO.thumbnailURL],
                    source = result[DrugDAO.sourceId]?.let { Source(id = result[DrugDAO.sourceId]) },
                    brand = result[DrugDAO.sourceId]?.let { Brand(id = result[DrugDAO.brandId]) },
                    creator = User(id = result[DrugDAO.creatorId])
                )

                val drugComponents = DrugDAO
                    .innerJoin(DrugComponentDAO, { id }, { componentID })
                    .innerJoin(componentOfDrugTable, { DrugComponentDAO.drugID }, { componentOfDrugTable[DrugDAO.id] })
                    .slice(fields)
                    .select { DrugComponentDAO.componentID eq result[DrugDAO.id] }

                drugComponents.map {
                    val componentOfDrug = Drug(
                        id = it[componentOfDrugTable[DrugDAO.id]]
                    )

                    if (includedResources?.contains("drugComponents") == true) {
                        componentOfDrug.name = it[componentOfDrugTable[DrugDAO.name]]
                        componentOfDrug.thumbnailURL = it[componentOfDrugTable[DrugDAO.thumbnailURL]]
                        componentOfDrug.purity = it[DrugComponentDAO.purity]
                        componentOfDrug.quantity = it[DrugComponentDAO.quantity]
                        componentOfDrug.measurementUnit = it[DrugComponentDAO.measurementUnit]
                        componentOfDrug.componentOf = Drug(id = it[DrugComponentDAO.componentID])
                    }

                    componentsOfDrug.add(componentOfDrug)
                }

                if (componentsOfDrug.isNotEmpty()) {
                    drug.components = componentsOfDrug
                }

                includedResources?.let {
                    if (includedResources.contains("brands")) {
                        drug.brand?.name = result[BrandDAO.name]
                    }

                    if (includedResources.contains("sources")) {
                        drug.source?.name = result[SourceDAO.name]
                    }
                }

                drugs.add(drug)
            }
        }

        return drugs
    }

    // Get drug records which are verified
    private fun withVerifiedDrugs(query: Query) {
        if (!withVerified) return

        val verifiedUser = UserDAO.alias("verifiedUser")

        query.adjustColumnSet { innerJoin(verifiedUser, { DrugDAO.creatorId }, { verifiedUser[UserDAO.id] }) }
            .orWhere { verifiedUser[UserDAO.verified] eq true }
    }

    // Get the brand record of the drug
    private fun withBrand(query: Query, fields: MutableList<Column<*>>) {
        query.adjustColumnSet { leftJoin(BrandDAO, { DrugDAO.brandId }, { id }) }
        fields += BrandDAO.columns
    }

    // Get the source record of the drug
    private fun withSource(query: Query, fields: MutableList<Column<*>>) {
        query.adjustColumnSet { leftJoin(SourceDAO, { DrugDAO.sourceId }, { id }) }
        fields += SourceDAO.columns
    }

    // Include the asked resources in the query
    private fun withIncludedResources(query: Query) {
        includedResources?.let {
            val fields = DrugDAO.columns.toMutableList()

            if (includedResources.contains("brands")) {
                withBrand(query, fields)
            }

            if (includedResources.contains("sources")) {
                withSource(query, fields)
            }

            query.adjustSlice { slice(fields) }
        }
    }
}


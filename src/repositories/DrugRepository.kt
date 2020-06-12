package com.medtracker.repositories

import com.medtracker.models.*
import com.medtracker.repositories.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DrugRepository(
    private val includedResources: List<String>?,
    private val withVerified: Boolean
) {

    /**
     * Start a transaction to get a collection of drug records with the corresponding creator id.
     * Add verified drug records to this collection if the "withVerified" is true.
     * Add the requested resources to this collection if it is present in the "includedResources" list.
     * Convert the drug records with the requested resources into drug objects and send it back.
     */
    fun getAllByCreator(creatorId: Int): ArrayList<Drug> {
        val drugs: ArrayList<Drug> = arrayListOf()

        transaction {
            val drugRecords = DrugDAO.select { DrugDAO.creatorId eq creatorId }

            withVerifiedDrugs(drugRecords)
            withIncludedResources(drugRecords)

            drugRecords.mapTo(drugs) { result ->
                val componentsOfDrug = withComponents(result[DrugDAO.creatorId])
                val containers = withContainers(result[DrugDAO.creatorId])

                val drug = Drug(
                    id = result[DrugDAO.id],
                    name = result[DrugDAO.name],
                    thumbnailURL = result[DrugDAO.thumbnailURL],
                    source = result[DrugDAO.sourceId]?.let { Source(id = result[DrugDAO.sourceId]) },
                    brand = result[DrugDAO.sourceId]?.let { Brand(id = result[DrugDAO.brandId]) },
                    components = componentsOfDrug,
                    containers = containers
                )

                includedResources?.let {
                    if (includedResources.contains("brands")) {
                        drug.brand?.name = result[BrandDAO.name]
                    }

                    if (includedResources.contains("sources")) {
                        drug.source?.name = result[SourceDAO.name]
                    }
                }

                drug
            }
        }

        return drugs
    }

    // Get all drugComponentsRecords by the drugID and set the (Drug)component object with the record
    private fun withContainers(drugId: Int): ArrayList<Container> {
        val containers: ArrayList<Container> = arrayListOf()
        val fields = if (includedResources?.contains("containers") == true) {
            ContainerDAO.columns
        } else {
            listOf(ContainerDAO.id)
        }

        val containerRecords = DrugDAO
            .innerJoin(DrugContainerDAO, { id }, { DrugContainerDAO.drugId})
            .innerJoin(ContainerDAO, { DrugContainerDAO.containerId }, { id })
            .slice(fields)
            .select { DrugContainerDAO.drugId eq drugId }

        return containerRecords.mapTo(containers) {
            val container = Container(
                id = it[ContainerDAO.id]
            )

            if (includedResources?.contains("containers") == true) {
                container.name = it[ContainerDAO.name]
                container.quantity = it[ContainerDAO.quantity]
                container.measurementUnit = it[ContainerDAO.measurementUnit]
                container.thumbnailURL = it[ContainerDAO.thumbnailURL]
            }

            container
        }
    }

    // Get all drugComponentsRecords by the drugID and set the drugComponent object with the record
    private fun withComponents(drugId: Int): ArrayList<Drug> {
        val components: ArrayList<Drug> = arrayListOf()
        val componentOfDrugTable = DrugDAO.alias("componentOfDrugTable")
        val fields = if (includedResources?.contains("components") == true) {
            componentOfDrugTable.columns + DrugComponentDAO.columns
        } else {
            listOf(componentOfDrugTable[DrugDAO.id])
        }

        val drugComponentRecords = DrugDAO
            .innerJoin(DrugComponentDAO, { id }, { componentId })
            .innerJoin(componentOfDrugTable, { DrugComponentDAO.drugId }, { componentOfDrugTable[DrugDAO.id] })
            .slice(fields)
            .select { DrugComponentDAO.componentId eq drugId }

        return drugComponentRecords.mapTo(components) {
            val componentOfDrug = Drug(
                id = it[componentOfDrugTable[DrugDAO.id]]
            )

            if (includedResources?.contains("components") == true) {
                componentOfDrug.name = it[componentOfDrugTable[DrugDAO.name]]
                componentOfDrug.thumbnailURL = it[componentOfDrugTable[DrugDAO.thumbnailURL]]
                componentOfDrug.purity = it[DrugComponentDAO.purity]?.toDouble()
                componentOfDrug.quantity = it[DrugComponentDAO.quantity]
                componentOfDrug.measurementUnit = it[DrugComponentDAO.measurementUnit]
                componentOfDrug.componentOf = Drug(id = it[DrugComponentDAO.componentId])
            }

            componentOfDrug
        }
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

    // Include the requested resources in the query
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
package com.medtracker.services.FDTOParsers

import com.medtracker.models.Drug
import com.medtracker.services.dto.DrugRDTO
import com.medtracker.services.dto.DrugsRDTO

class DrugsParser(private val drugsRDTO: DrugsRDTO) {

    fun parse(drugs: ArrayList<Drug>){
        drugs.map{
            val drugRDTO = DrugRDTO()
            val drugParser = DrugParser(drugRDTO)

            drugParser.parse(it)

            drugsRDTO.data.add(drugRDTO)
        }
    }
}
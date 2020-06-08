package com.medtracker.services.dto

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import com.medtracker.models.Drug
import com.medtracker.repositories.dao.WeightOrVolume

data class DrugsRDTO(
    var data: ArrayList<DrugRDTO> = arrayListOf()
)

data class DrugRDTO(
    val type: String = "drugs",
    var id: Int? = null


//    val title: String,
//    val note: String? = null,
//    val quantity: Int,
//    val measurementUnit: WeightOrVolume,
//    val consumedAt: String,
//    val creatorID: Int,
//    val drugID: Int,
//    val containerID: Int? = null
)
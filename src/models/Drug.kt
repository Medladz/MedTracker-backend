package com.medtracker.models

import com.medtracker.utilities.ifLet

class Drug(
    var id: Int? = null,
    var name: String? = null,
    var thumbnailURL: String? = null,
    var purity: Double? = null,
    var quantity: Int? = null,
    var measurementUnit: String? = null,
    var brand: Brand? = null,
    var source: Source? = null,
    var components: ArrayList<Drug> = ArrayList(),
    var componentOf: Drug? = null,
    var creator: User? = null
) {

    // Return the percentage of the drug with the symbol
    fun getPurityPercentage(): String? {
        return purity.let { purity.toString() + "%" }
    }

    // Return the quantity of the drug with the used measurementUnit
    fun getQuantityWithUnit(): String? {
        ifLet(quantity, measurementUnit) {(q, ms) ->
            return q.toString() + ms.toString()
        }
        return null
    }
}
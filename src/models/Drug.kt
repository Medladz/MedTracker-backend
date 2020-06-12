package com.medtracker.models

import com.medtracker.repositories.enumTypes.WeightOrVolume
import com.medtracker.utilities.ifLet

class Drug(
    var id: Int? = null,
    var name: String? = null,
    var thumbnailURL: String? = null,
    var purity: Double? = null,
    var quantity: Int? = null,
    var measurementUnit: WeightOrVolume? = null,
    var brand: Brand? = null,
    var source: Source? = null,
    var components: ArrayList<Drug> = ArrayList(),
    var containers: ArrayList<Container> = ArrayList(),
    var componentOf: Drug? = null,
    var creator: User? = null
) {

    // Return the percentage of the drug with the symbol
    fun getPurityPercentage(): String? {
        return purity.let { purity.toString() + "%" }
    }

    // Return the quantity of the drug with the used measurementUnit
    fun getQuantityWithUnit(): String? {
        ifLet(quantity, measurementUnit) {(quantity, measurementUnit) ->
            return quantity.toString() + measurementUnit.toString()
        }
        return null
    }
}
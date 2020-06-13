package com.medtracker.models

import com.medtracker.repositories.enumTypes.WeightOrVolume
import com.medtracker.utilities.ifLet

class Container(
    var id: Int? = null,
    var creatorID: Int? = null,
    var name: String? = null,
    var quantity: Int? = null,
    var measurementUnit: WeightOrVolume? = null,
    var thumbnailURL: String? = null
) {

    // Return the quantity of the container with the used measurementUnit
    fun getQuantityWithUnit(): String? {
        ifLet(quantity, measurementUnit) {(quantity, measurementUnit) ->
            return quantity.toString() + measurementUnit.toString()
        }
        return null
    }
}

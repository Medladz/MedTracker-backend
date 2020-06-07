package com.medtracker.models

import com.medtracker.repositories.dao.WeightOrVolume
import org.joda.time.DateTime

class Agenda(
    var id: Int? = null,
    var creator: User? = null,
    var drug: Drug? = null,
    var container: Container? = null,
    var title: String? = null,
    var note: String? = null,
    var quantity: Int? = null,
    var measurementUnit: WeightOrVolume? = null,
    var consumedAt: DateTime? = null
)
package com.medtracker.models

import com.medtracker.repositories.enumTypes.MeasurementUnit
import org.joda.time.DateTime

class Agenda(
    var id: Int? = null,
    var creator: User? = null,
    var drug: Drug? = null,
    var container: Container? = null,
    var title: String? = null,
    var note: String? = null,
    var quantity: Int? = null,
    var measurementUnit: MeasurementUnit? = null,
    var consumedAt: DateTime? = null
)
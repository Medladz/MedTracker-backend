package com.medtracker.models

import org.joda.time.DateTime
import org.w3c.dom.Text
import java.sql.Timestamp

class Agenda(
    var id: Int? = null,
    var creatorID: Int? = null,
    var drugID: Int? = null,
    var title: String? = null,
    var note: Text? = null,
    var quantity: Int? = null,
    var measurementUnit: String? = null,
    var consumedAt: DateTime? = null
)
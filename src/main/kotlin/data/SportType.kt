package data

import annotations.SampleModel
import annotations.StringField
import java.util.*

@SampleModel
data class SportType(
    @StringField("uuid-string")
    val id: UUID,
    @StringField("football")
    val name: String,
)

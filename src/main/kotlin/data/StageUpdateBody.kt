package data

import annotations.SampleModel
import annotations.StringField

@SampleModel
data class StageUpdateBody(
    @StringField("Round 1 Qualifying")
    val name: String?,
)

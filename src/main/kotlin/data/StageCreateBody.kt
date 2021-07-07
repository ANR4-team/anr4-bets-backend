package data

import annotations.SampleModel
import annotations.StringField

@SampleModel
data class StageCreateBody(
    @StringField("Round 1 Qualifying")
    val name: String,
    @StringField("custom")
    val type: StageType,
)

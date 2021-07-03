package data

import annotations.SampleModel
import annotations.StringField

@SampleModel
data class SportTypeBody(
    @StringField("Dota 2")
    val name: String,
)

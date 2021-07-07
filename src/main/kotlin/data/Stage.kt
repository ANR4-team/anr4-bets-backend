package data

import annotations.IntField
import annotations.SampleModel
import annotations.StringField

@SampleModel
data class Stage(
    @IntField(332)
    val id: Int,
    @StringField("Round 1 Qualifying")
    val name: String,
    @StringField("custom")
    val type: StageType,
)
